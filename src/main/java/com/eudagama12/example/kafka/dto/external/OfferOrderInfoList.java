package com.eudagama12.example.kafka.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferOrderInfoList implements Serializable {

    @JsonProperty("OfferOrderInfo")
    private OfferOrderInfo offerOrderInfo;

}
