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
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(KafkaListenerBinding.class)
public class KafkaListenerService {

    @Autowired
    public RecordBuilder recordBuilder;

    @StreamListener("input-channel-1")
    public void process(KStream<String, PosInvoice> input){
        KStream<String, HadoopRecord> hadoopRecordKStream = input
                .mapValues( v -> recordBuilder.getMaskedInvoice(v))
                .flatMapValues( v -> recordBuilder.getHadoopRecords(v));

        KStream<String, Notification> notificationKStream = input
                .filter((k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .mapValues(v -> recordBuilder.getNotification(v));

        hadoopRecordKStream.foreach((k, v) -> log.info(String.format("Hadoop Record:- Key: %s, Value: %s", k, v)));
        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));

        hadoopRecordKStream.to("hadoop-sink-topic", Produced.with(Serdes.String(), AppSerde.HadoopRecord()));
        notificationKStream.to("loyalty-topic",Produced.with(Serdes.String(),AppSerde.Notification()));



    }
}
