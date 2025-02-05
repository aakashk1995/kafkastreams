package org.example.streams.serde;

import org.example.streams.model.HadoopRecord;
import org.springframework.kafka.support.serializer.JsonSerde;

public class HadoopRecordJsonSerde extends JsonSerde<HadoopRecord>
{
}
