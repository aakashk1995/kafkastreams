package org.example.streams.serde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.example.streams.serializers.HadoopRecordDeserializer;
import org.example.streams.serializers.HadoopRecordSerializer;
import org.example.streams.serializers.NotificationDeserializer;
import org.example.streams.serializers.NotificationSerializer;

public class AppSerde {

    public static Serde<Notification> Notification(){
        Serde<Notification> notificationSerde = Serdes.serdeFrom(new NotificationSerializer(),new NotificationDeserializer());
        return notificationSerde;
    }
    public static Serde<HadoopRecord> HadoopRecord(){
        Serde<HadoopRecord> hadoopRecordSerde = Serdes.serdeFrom(new HadoopRecordSerializer(),new HadoopRecordDeserializer());
        return hadoopRecordSerde;
    }
}
