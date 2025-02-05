package org.example.streams.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.kafka.support.serializer.JsonSerde;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification  {

    @JsonProperty("InvoiceNumber")
    private String InvoiceNumber;
    @JsonProperty("CustomerCardNo")
    private String CustomerCardNo;
    @JsonProperty("TotalAmount")
    private Double TotalAmount;
    @JsonProperty("EarnedLoyaltyPoints")
    private Double EarnedLoyaltyPoints;
}
