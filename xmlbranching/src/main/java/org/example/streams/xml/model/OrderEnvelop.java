package org.example.streams.xml.model;

import org.example.streams.xml.model.Order;
import lombok.Data;

@Data
public class OrderEnvelop {
    String xmlOrderKey;
    String xmlOrderValue;

    String orderTag;
     Order validOrder;
}
