package com.eudagama12.example.kafka.dto.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "consumer_offset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumerOffset {

    @EmbeddedId
    private ConsumerOffsetKey consumerOffsetKey;

    @Column(name = "offset")
    private Long offset;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST")
    @Column(name = "received")
    private Date received;

}