package com.eudagama12.example.kafka.dto.internal;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumerOffsetKey implements Serializable {

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Column(name = "partition_id", nullable = false, length = 10)
    private int partitionId;

}