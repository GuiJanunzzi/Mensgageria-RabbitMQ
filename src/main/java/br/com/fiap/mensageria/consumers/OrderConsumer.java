package br.com.fiap.mensageria.consumers;

import br.com.fiap.mensageria.config.RabbitConfig;
import br.com.fiap.mensageria.model.OrderCreatedMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME, containerFactory = "rabbitListenerContainerFactory")
    public void processOrderCreated(OrderCreatedMessage message,
                                    Channel channel,
                                    @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            // lógica de negócio aqui
            log.info("Processing message: " + message);
            // se tudo OK:
            channel.basicAck(tag, false);
        } catch (Exception ex) {
            // decisão: requeue ou descartar
            boolean requeue = false; // ou true, conforme lógica
            channel.basicNack(tag, false, requeue);
        }
    }
}