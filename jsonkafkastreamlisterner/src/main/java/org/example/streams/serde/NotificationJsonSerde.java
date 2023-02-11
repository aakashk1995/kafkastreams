package org.example.streams.serde;

import org.example.streams.model.Notification;
import org.springframework.kafka.support.serializer.JsonSerde;

public class NotificationJsonSerde extends JsonSerde<Notification> {
}
