package org.example.streams.services;


import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.example.streams.bindings.KafkaListenerBinding;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.example.streams.serde.AppSerde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(KafkaListenerBinding.class)
public class KafkaListenerServiceReduce {

    @Autowired
    public RecordBuilder recordBuilder;

   // @StreamListener("input-channel-1")
    public void process(KStream<String, PosInvoice> input){

        KStream<String, Notification> notificationKStream = input
                .filter((k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .map((key,value)-> new KeyValue<>(value.getCustomerCardNo(),recordBuilder.addTotalLoyaltyPoints(value)))
                .groupByKey(Grouped.with(Serdes.String(),AppSerde.Notification()))
                .reduce((aggValue, newValue) -> {
                    newValue.setTotalLoyaltyPoints(newValue.getEarnedLoyaltyPoints() + aggValue.getTotalLoyaltyPoints());
                    return newValue;
                }).toStream();



        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));


       //notificationKStream.to("loyalty-topic",Produced.with(Serdes.String(),AppSerde.Notification()));

    }
}
