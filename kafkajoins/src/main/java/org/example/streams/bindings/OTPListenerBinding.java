package org.example.streams.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.example.streams.model.PaymentConfirmation;
import org.example.streams.model.PaymentRequest;
import org.example.streams.model.PosInvoice;
import org.springframework.cloud.stream.annotation.Input;

public interface OTPListenerBinding {

    @Input("payment-request-channel")
    KStream<String, PaymentRequest> requestInputStream();

    @Input("payment-confirmation-channel")
    KStream<String, PaymentConfirmation> confirmationInputStream();
}
