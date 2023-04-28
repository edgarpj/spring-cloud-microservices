package com.example.productsservice.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class FeignConfiguration {

        @Bean
        public Decoder decoder() {
            return new JacksonDecoder();
        }

        @Bean
        public Encoder encoder() {
            return new JacksonEncoder();
        }
}
