package org.example.streams.services;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.example.streams.bindings.KafkaListenerBinding;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(KafkaListenerBinding.class)
public class KafkaListenerService {

    @StreamListener("input-channel-1")
    public void process(KStream<String, String> input){
        input.foreach((k,v) -> log.info(String.format("Key: %s, Value: %s",k,v)));
    }
}
