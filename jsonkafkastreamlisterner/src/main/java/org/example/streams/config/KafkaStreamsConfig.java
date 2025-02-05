//package org.example.streams.config;
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.StreamsConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.EnableKafkaStreams;
//import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
//import org.springframework.kafka.config.KafkaStreamsConfiguration;
//import org.springframework.kafka.support.serializer.JsonSerde;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
//
//@Configuration
//@EnableKafka
//@EnableKafkaStreams
//public class KafkaStreamsConfig {
//
//    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
//    KafkaStreamsConfiguration kStreamsConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "jsonkafkastreamlisterner");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass().getName());
//
//        return new KafkaStreamsConfiguration(props);
//    }
//}
