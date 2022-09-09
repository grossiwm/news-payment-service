package org.acme;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


import io.quarkus.runtime.StartupEvent;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

@ApplicationScoped
public class MessageService {

    @Inject
    PaymentService paymentService;

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private Channel channel;

    public void onApplicationStart(@Observes StartupEvent event) throws Exception {
        // on application start prepare the queus and message listener
        setupChannel();
        setupReceiving();
    }

    private void setupChannel() throws Exception {
        try {
            // create a connection
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            Connection connection = connectionFactory.newConnection();
            // create a channel
            channel = connection.createChannel();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setupReceiving() {
        try {
            // register a consumer for messages
            channel.basicConsume("paymentsToProcessQueue", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    log.info("Received payment: " + new String(body, StandardCharsets.UTF_8));
                    String payment = paymentService.process(new String(body, StandardCharsets.UTF_8));
                    try {
                        send(payment);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IOException();
                    }
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void send(String message) throws Exception {
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
               .contentType("application/json")
               .build();
        // send a message to the exchange
        channel.basicPublish("paymentsExchange", "paymentsProcessedQueue", basicProperties, message.getBytes(StandardCharsets.UTF_8));

    }
}