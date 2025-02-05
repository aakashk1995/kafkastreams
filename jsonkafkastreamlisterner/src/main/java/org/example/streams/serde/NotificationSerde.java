package org.example.streams.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.example.streams.model.Notification;
import org.example.streams.serializers.NotificationDeserializer;
import org.example.streams.serializers.NotificationSerializer;

import java.util.Map;

public class NotificationSerde implements Serde<Notification> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serde.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Serde.super.close();
    }

    @Override
    public Serializer<Notification> serializer() {
        NotificationSerializer notificationSerializer = new NotificationSerializer();
        return notificationSerializer;
    }

    @Override
    public Deserializer<Notification> deserializer() {
        NotificationDeserializer notificationDeserializer = new NotificationDeserializer();
        return notificationDeserializer;
    }
}
