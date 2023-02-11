package org.example.streams.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface KafkaListenerBinding {

    @Input("notification-input-channel")
    KStream<String, PosInvoice> notificationInputStream();

    @Output("notification-output-channel")
    KStream<String, Notification> notificationOutputStream();

//    @Input("hadoop-input-channel")
//    KStream<String, PosInvoice> hadoopInputStream();

    @Output("hadoop-output-channel")
    KStream<String, HadoopRecord> hadoopOutputStream();
}
