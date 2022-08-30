package com.eudagama12.example.kafka.dto.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsumerOffsetRepository extends JpaRepository<ConsumerOffset, ConsumerOffsetKey> {
    @Query("SELECT c.offset FROM ConsumerOffset c WHERE c.consumerOffsetKey = ?1")
    Long getLastCommitOffset(ConsumerOffsetKey consumerOffsetKey);
}