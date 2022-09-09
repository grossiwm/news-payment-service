package org.acme;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

@ApplicationScoped
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    
    public String process(String paymentJSON) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Payment payment = objectMapper.readValue(paymentJSON, Payment.class);
            log.info("About to process Payment " + payment.id);
            Calendar calendar = Calendar.getInstance();
            payment.processedDate = calendar;
            payment.creditCardDetails = null;
            TimeUnit.SECONDS.sleep(2);
            log.info("Payment " + payment.id + " processed");
            return objectMapper.writeValueAsString(payment);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
