package org.example.streams.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.example.streams.config.PropertiesFile;
import org.example.streams.model.PosInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, PosInvoice> kafkaTemplate;

    public void sendMessage(PosInvoice invoice) throws JsonProcessingException {
       log.info(String.format("Producing Invoice No: %s Customer Type: %s",
               invoice.getInvoiceNumber(),
               invoice.getCustomerType()));
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(invoice);
        System.out.println(jsonString);
       kafkaTemplate.send(PropertiesFile.TOPIC_NAME,invoice.getStoreID(),invoice);
    }
}
