package org.example.streams.services;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.example.streams.bindings.KafkaListenerBinding;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@EnableBinding(KafkaListenerBinding.class)
public class NotificationProcessorService {


    @Autowired
    RecordBuilder recordBuilder;

    @StreamListener("notification-input-channel")
    @SendTo("notification-output-channel")
    public KStream<String, Notification> notificationKStream(KStream<String, PosInvoice> input) {
        //toMap keyvalue pair
        //KStream<String, String> invoiceKStream =
          //      input.filter((k,v)-> v.getCustomerType().equalsIgnoreCase("PRIME"))
            //    .map((k,v)-> KeyValue.pair(k.toUpperCase(),v.getInvoiceNumber()));

        KStream<String, Notification> notificationKStream = input
                .filter((k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .mapValues(v -> recordBuilder.getNotification(v));

        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));

        return notificationKStream;
    }
    @StreamListener("notification-input-channel")
    @SendTo("hadoop-output-channel")
    public KStream<String, HadoopRecord> hadoopRecordKStream(KStream<String, PosInvoice> input) {
        KStream<String, HadoopRecord> hadoopRecordKStream = input
                .mapValues( v -> recordBuilder.getMaskedInvoice(v))
                .flatMapValues( v -> recordBuilder.getHadoopRecords(v));

        hadoopRecordKStream.foreach((k, v) -> log.info(String.format("Hadoop Record:- Key: %s, Value: %s", k, v)));

        return hadoopRecordKStream;
    }
}
