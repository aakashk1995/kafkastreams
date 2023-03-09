package org.example.streams.services;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
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
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Log4j2
@Service
@EnableBinding(KafkaListenerBinding.class)
public class KafkaListenerService {

    @Autowired
    public RecordBuilder recordBuilder;

   @StreamListener("input-channel-1")
    public void process(KStream<String, PosInvoice> input){
       input.peek((k, v) -> log.info("Key = " + k + " Created Time = " + Instant.ofEpochMilli(v.getCreatedTime()).atOffset(ZoneOffset.UTC)))
               .map((k,v)->new KeyValue<>(v.getStoreID(),v))
               .groupByKey(Grouped.with(Serdes.String(),AppSerde.PosInvoiceSerde()))
               .windowedBy(TimeWindows.of(Duration.ofMinutes(5)))
               .count()
               .toStream()
               .foreach((k, v) -> log.info(
                       "StoreID: " + k.key() +
                               " Window start: " +
                               Instant.ofEpochMilli(k.window().start())
                                       .atOffset(ZoneOffset.UTC) +
                               " Window end: " +
                               Instant.ofEpochMilli(k.window().end())
                                       .atOffset(ZoneOffset.UTC) +
                               " Count: " + v +
                               " Window#: " + k.window().hashCode()
               ));





    }
}
