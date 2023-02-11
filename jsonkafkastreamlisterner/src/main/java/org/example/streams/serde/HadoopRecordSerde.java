/*
package org.example.streams.serde;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.example.streams.model.HadoopRecord;
import org.example.streams.serializers.HadoopRecordDeserializer;
import org.example.streams.serializers.HadoopRecordSerializer;
import java.util.Map;

public class HadoopRecordSerde implements Serde<HadoopRecord> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serde.super.configure(configs, isKey);
    }

    @Override
    public void close() {
        Serde.super.close();
    }

    @Override
    public Serializer<HadoopRecord> serializer() {

        return new HadoopRecordSerializer();
    }

    @Override
    public Deserializer<HadoopRecord> deserializer() {
        return new HadoopRecordDeserializer();
    }
}
*/

