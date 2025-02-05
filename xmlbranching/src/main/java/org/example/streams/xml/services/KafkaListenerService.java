package org.example.streams.xml.services;


import org.apache.kafka.common.serialization.Serdes;
import org.example.streams.xml.model.Order;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.example.streams.xml.bindings.OrderListenerBinding;
import org.example.streams.xml.config.AppConstants;
import org.example.streams.xml.model.OrderEnvelop;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.util.Locale;

import static org.example.streams.xml.config.AppConstants.ERROR_TOPIC;

@Log4j2
@Service
@EnableBinding(OrderListenerBinding.class)
public class KafkaListenerService {

    @StreamListener("xml-input-channel")
    @SendTo({"india-orders-channel", "abroad-orders-channel"})
    public KStream<String, Order>[] process(KStream<String, String> input) {

        input.foreach((k, v) -> log.info(String.format("Received XML Order Key: %s, Value: %s", k, v)));

        KStream<String, OrderEnvelop> orderEnvelopKStream = input.map((key, value) -> {
            OrderEnvelop orderEnvelop = new OrderEnvelop();
            orderEnvelop.setXmlOrderKey(key);
            orderEnvelop.setXmlOrderValue(value);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                orderEnvelop.setValidOrder((Order) jaxbUnmarshaller.unmarshal(new StringReader(value)));
                orderEnvelop.setOrderTag(AppConstants.VALID_ORDER);

                if(orderEnvelop.getValidOrder().getShipTo().getCity().isEmpty()){
                    log.error("Missing destination City");
                    orderEnvelop.setOrderTag(AppConstants.ADDRESS_ERROR);
                }

            } catch (JAXBException e) {
                log.error("Failed to Unmarshal the incoming XML");
                orderEnvelop.setOrderTag(AppConstants.PARSE_ERROR);
            }
            return KeyValue.pair(orderEnvelop.getOrderTag(), orderEnvelop);
        });


     //   orderEnvelopKStream.filter((k, v) -> !k.equalsIgnoreCase(AppConstants.VALID_ORDER))
     //           .to(ERROR_TOPIC, Produced.with(Serdes.String(), AppSerdes.OrderEnvelop()));

        KStream<String, Order> validOrders = orderEnvelopKStream
                .filter((k, v) -> k.equalsIgnoreCase(AppConstants.VALID_ORDER))
                .map((k, v) -> KeyValue.pair(v.getValidOrder().getOrderId(), v.getValidOrder()));

        validOrders.foreach((k, v) -> log.info(String.format("Valid Order with ID: %s", v.getOrderId())));
        Predicate<String, Order> isIndiaOrder = (k, v) -> v.getShipTo().getCountry().equalsIgnoreCase("india");
        Predicate<String, Order> isAbroadOrder = (k, v) -> !v.getShipTo().getCountry().equalsIgnoreCase("india");

        KStream<String, Order> validOrders2 =    orderEnvelopKStream
                .filter((k,v)->k.equalsIgnoreCase(AppConstants.VALID_ORDER))
                .filter((k,v)->v.getValidOrder().getShipTo().getCountry().equalsIgnoreCase("india"))
                .map((k,v)-> KeyValue.pair(v.getValidOrder().getOrderBy(),v.getValidOrder()))
                .filter(isIndiaOrder);


        return validOrders.branch(isIndiaOrder, isAbroadOrder);

    }
}
