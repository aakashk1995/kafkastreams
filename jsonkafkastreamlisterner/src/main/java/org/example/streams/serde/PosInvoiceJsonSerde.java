package org.example.streams.serde;

import org.example.streams.model.PosInvoice;
import org.springframework.kafka.support.serializer.JsonSerde;

public class PosInvoiceJsonSerde extends JsonSerde<PosInvoice> {
}
