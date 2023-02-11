package org.example.streams;


import org.apache.kafka.common.serialization.Serde;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

@SpringBootApplication
public class JsonKafkaStreamListerner {

    public static void main(String[] args) {
        SpringApplication.run(JsonKafkaStreamListerner.class, args);
    }
    @Bean
    public Serde<Notification> notificationSerde() {
        return new JsonSerde(Notification.class);
    }
    @Bean
    public Serde<HadoopRecord> hadoopRecordSerde() {
        return new JsonSerde(HadoopRecord.class);
    }
}

