package org.example.streams.serializers;

import org.apache.kafka.common.header.Headers;
import org.example.streams.model.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


public class NotificationSerializer implements Serializer<Notification> {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public byte[] serialize(String s, Notification data) {
        if (Objects.isNull(data)) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing message",
                    e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
