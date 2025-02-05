package org.example.streams.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.streams.model.HadoopRecord;

import java.util.Map;
import java.util.Objects;

public class HadoopRecordDeserializer implements Deserializer<HadoopRecord> {
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
    public HadoopRecord deserialize(String topic, byte[] bytes) {
        if (Objects.isNull(bytes)) {
            return null;
        }
        HadoopRecord data;
        try {
            data = objectMapper.treeToValue(objectMapper.readTree(bytes),
                    HadoopRecord.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }


}
