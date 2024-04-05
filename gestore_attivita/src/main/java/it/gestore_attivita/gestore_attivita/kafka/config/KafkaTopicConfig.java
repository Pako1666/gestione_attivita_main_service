package it.gestore_attivita.gestore_attivita.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class KafkaTopicConfig {

    @Value("${kafka.topic-name}")
    private String name;

    @Bean
    public NewTopic gestioneAttivitaKafka(){
        log.info(String.format("Nome topic kafka: %s",name));
        return TopicBuilder
                .name(name)
                .build();
    }

}
