package org.example.streams;


import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.extern.log4j.Log4j2;
import org.example.streams.config.PropertiesFile;
import org.example.streams.service.KafkaProducerService;
//import org.example.streams.service.datagenerator.InvoiceGenerator;
import org.example.streams.service.datagenerator.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class Main implements ApplicationRunner {

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private InvoiceGenerator invoiceGenerator;


    public static void main(String[] args) {
        System.out.println(PropertiesFile.COUNT);
        System.out.println(PropertiesFile.TOPIC_NAME);
        SpringApplication.run(Main.class, args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for(int i=0;i<PropertiesFile.COUNT;i++){
          //  producerService.sendMessage(invoiceGenerator.getNextInvoice());
            Thread.sleep(1000);
        }
    }
}