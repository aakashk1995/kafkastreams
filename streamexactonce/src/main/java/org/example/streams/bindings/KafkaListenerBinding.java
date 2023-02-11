package org.example.streams.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.example.streams.model.PosInvoice;
import org.springframework.cloud.stream.annotation.Input;

public interface KafkaListenerBinding {

    @Input("input-channel-1")
    KStream<String, PosInvoice> inputStream();
}
