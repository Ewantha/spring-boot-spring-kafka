package com.eudagama12.example.kafka.listner;

import com.eudagama12.example.kafka.dto.external.WorkOrder;
import com.eudagama12.example.kafka.dto.internal.ConsumerOffset;
import com.eudagama12.example.kafka.dto.internal.ConsumerOffsetKey;
import com.eudagama12.example.kafka.dto.internal.ConsumerOffsetRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class Consumer implements ConsumerSeekAware {

    private final ConsumerOffsetRepository consumerOffsetRepository;

    @Autowired
    public Consumer(ConsumerOffsetRepository consumerOffsetRepository) {
        this.consumerOffsetRepository = consumerOffsetRepository;
    }

    /**
     * Main kafka listener method. Flow starts here when work order message is received.
     * @param workOrder Consumed work order
     * @param timestamp Received time
     * @param offset Received Kafka offset
     * @param partitionId Received Kafka partition id
     * @param topic Received Kafka topic
     */
    @KafkaListener(topics = "${kafka.workOrder.topicName}")
    public void listenWorkOrder(@Payload WorkOrder workOrder,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                                @Header(KafkaHeaders.OFFSET) long offset,
                                @Header(value = KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
                                @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic) {
        log.info("Consumed work order: {} at {} partition {} offset {} topic {}", workOrder, timestamp, partitionId, offset, topic);

        saveCommitOffset(timestamp, offset, partitionId, topic);
    }

    /**
     * Before consuming a new message configure last commit offset to capture message without a miss.
     * @param assignments Kafka params
     * @param callback Kafka params
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        try {
            assignments.forEach((t, o) -> {
                Long lastCommitOffset = consumerOffsetRepository
                        .getLastCommitOffset(new ConsumerOffsetKey(t.topic(), t.partition()));

                if (lastCommitOffset != null) {
                    callback.seek(t.topic(), t.partition(), lastCommitOffset);
                }
            });
        } catch (Exception e) {
            log.error("Failed to assign offset:{}", e.getMessage());
        }
    }

    /**
     * Save successfully commit offset in DB.
     * @param timestamp Received timestamp
     * @param offset Received offset
     * @param partitionId Received partition id
     * @param topic Received topic
     */
    protected void saveCommitOffset(long timestamp, long offset, int partitionId, String topic) {
        try {
            ConsumerOffsetKey consumerOffsetKey = new ConsumerOffsetKey(topic, partitionId);
            ConsumerOffset consumerOffset = new ConsumerOffset(consumerOffsetKey, offset, new Date(timestamp));
            consumerOffsetRepository.save(consumerOffset);
        } catch (Exception e) {
            log.error("Failed to save offset:{}", e.getMessage());
        }
    }
}
