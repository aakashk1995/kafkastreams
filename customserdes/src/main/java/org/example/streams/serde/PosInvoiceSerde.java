package org.example.streams.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.example.streams.model.Notification;
import org.example.streams.model.PosInvoice;
import org.example.streams.serializers.NotificationDeserializer;
import org.example.streams.serializers.NotificationSerializer;
import org.example.streams.serializers.PosInvoiceDeserializer;
import org.example.streams.serializers.PosInvoiceSerializer;

import java.util.Map;

public class PosInvoiceSerde implements Serde<PosInvoice> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serde.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Serde.super.close();
    }

    @Override
    public Serializer<PosInvoice> serializer() {
        PosInvoiceSerializer posInvoiceSerializer = new PosInvoiceSerializer();
        return posInvoiceSerializer;
    }

    @Override
    public Deserializer<PosInvoice> deserializer() {
        PosInvoiceDeserializer posInvoiceDeserializer = new PosInvoiceDeserializer();
        return posInvoiceDeserializer;
    }
}
