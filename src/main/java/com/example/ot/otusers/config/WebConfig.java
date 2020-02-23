package com.example.ot.otusers.config;

import com.example.ot.otusers.util.RdfFormatConverter;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Queue myQueue() {
        return new Queue("amqpQueue", false);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RdfFormatConverter());
    }

}
