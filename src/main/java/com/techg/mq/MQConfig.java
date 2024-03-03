package com.techg.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import java.util.Queue;

@Configuration
public class MQConfig {

    public static final String QUEUE = "message_queue";
   public static final String EXCHANGE = "message_exchange";

    public static final String ROUTING_KEY = "message_routingKey";

    //This is the queue
    @Bean
    public Queue queue() {
        return  new Queue("QUEUE");
    }

    //This is the exchange
    @Bean
    public TopicExchange exchange() {

        return  new TopicExchange(EXCHANGE);
    }

    @Bean

    // method to bind the queue and the Exchange together
    public Binding binding(Queue queue, TopicExchange exchange){

        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    // convert particular message to json object
    @Bean

    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // create a RabbitMQ Template
    @Bean
    public  AmqpTemplate template(ConnectionFactory connectionFactory){
        //create an instance of a new rabbit template
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;


    }


}
