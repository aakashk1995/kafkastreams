package org.example.streams.configs;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.example.streams.model.PosInvoice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceTimeExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        PosInvoice invoice = (PosInvoice) consumerRecord.value();
        return ((invoice.getCreatedTime() > 0) ? invoice.getCreatedTime() : prevTime);
    }

    @Bean
    public TimestampExtractor invoiceTimesExtractor() {
        return new InvoiceTimeExtractor();
    }
}
