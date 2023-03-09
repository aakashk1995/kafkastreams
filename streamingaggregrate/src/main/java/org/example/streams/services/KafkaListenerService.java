package org.example.streams.services;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.example.streams.bindings.KafkaListenerBinding;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.example.streams.serde.AppSerde;
import org.example.streams.serializers.HadoopRecordDeserializer;
import org.example.streams.serializers.HadoopRecordSerializer;
import org.example.streams.serializers.NotificationDeserializer;
import org.example.streams.serializers.NotificationSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsStateStore;
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsStateStoreProperties;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Log4j2
@Service
@EnableBinding(KafkaListenerBinding.class)
public class KafkaListenerService {

    @Autowired
    public RecordBuilder recordBuilder;

    @StreamListener("word-channel-1")
   // @KafkaStreamsStateStore(name = "state-store", type = KafkaStreamsStateStoreProperties.StoreType.KEYVALUE)
    public void process(KStream<String, String> input){
        KStream<String, String> wordStream = input
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")));

        //group Ktable key by using value
        wordStream.groupBy((key, value) -> value)
                .count()
                .toStream()
                .peek((k, v) -> log.info("Word: {} Count: {}", k, v));

    }
}
