package org.example.streams.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.streams.model.Notification;

public class NotificationDeserializer implements Deserializer<Notification> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }

    @Override
    public Notification deserialize(String topic, byte[] bytes) {
        if (Objects.isNull(bytes)) {
            return null;
        }
        Notification data;
        try {
            data = objectMapper.treeToValue(objectMapper.readTree(bytes),
                    Notification.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }


}
