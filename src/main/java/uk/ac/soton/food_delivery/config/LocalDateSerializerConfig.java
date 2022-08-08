package uk.ac.soton.food_delivery.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: ZhanX
 * @Date: 2021-03-12 20:01:56
 */
@Configuration
public class LocalDateSerializerConfig {
    @Bean
    public LocalDateSerializer localDateDeserializer() {
        return new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    public LocalDateDeserializer localDateSerializer() {
        return new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDate.class, localDateDeserializer())
                .deserializerByType(LocalDateTime.class, localDateSerializer());
    }
}
