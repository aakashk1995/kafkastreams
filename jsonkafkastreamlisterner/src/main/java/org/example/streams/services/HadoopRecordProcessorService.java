//package org.example.streams.services;
//
//import lombok.extern.log4j.Log4j2;
//import org.apache.kafka.streams.kstream.KStream;
//import org.example.streams.bindings.KafkaListenerBinding;
////import org.example.streams.model.HadoopRecord;
//import org.example.streams.model.HadoopRecord;
//import org.example.streams.model.PosInvoice;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Service;
//
//@Service
//@Log4j2
//@EnableBinding(KafkaListenerBinding.class)
//public class HadoopRecordProcessorService {
//    @Autowired
//    RecordBuilder recordBuilder;
//
//    @StreamListener("notification-input-channel")
//    @SendTo("hadoop-output-channel")
//    public KStream<String, HadoopRecord> process(KStream<String, PosInvoice> input) {
//        KStream<String, HadoopRecord> hadoopRecordKStream = input
//                .mapValues( v -> recordBuilder.getMaskedInvoice(v))
//                .flatMapValues( v -> recordBuilder.getHadoopRecords(v));
//
//        hadoopRecordKStream.foreach((k, v) -> log.info(String.format("Hadoop Record:- Key: %s, Value: %s", k, v)));
//
//        return hadoopRecordKStream;
//    }
//}
