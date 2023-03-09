package org.example.streams.serde;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.example.streams.model.HadoopRecord;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.example.streams.serializers.*;

public class AppSerde {

    public static Serde<Notification> Notification(){
        Serde<Notification> notificationSerde = Serdes.serdeFrom(new NotificationSerializer(),new NotificationDeserializer());
        return notificationSerde;
    }
    public static Serde<HadoopRecord> HadoopRecord(){
        Serde<HadoopRecord> hadoopRecordSerde = Serdes.serdeFrom(new HadoopRecordSerializer(),new HadoopRecordDeserializer());
        return hadoopRecordSerde;
    }

    public static Serde<PosInvoice> PosInvoiceSerde(){
        Serde<PosInvoice> posInvoiceSerde = Serdes.serdeFrom(new PosInvoiceSerializer(),new PosInvoiceDeserializer());
        return posInvoiceSerde;
    }
}
