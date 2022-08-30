package com.eudagama12.example.kafka.dto.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferOrderInfo implements Serializable {

    @JsonProperty("OfferId")
    private String offerId;

    @JsonProperty("ExpireDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddhhmmss", timezone = "IST")
    private Date expireDate;

    @JsonProperty("EffectiveDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddhhmmss", timezone = "IST")
    private Date effectiveDate;

}
