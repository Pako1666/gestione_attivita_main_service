package it.gestore_attivita.gestore_attivita.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class KafkaTopicConfig {


    /*@Bean
    public NewTopic attivitaTopic(){

        log.info(String.format("Nome topic kafka: %s",KafkaTopicNames.ATTIVITA_TOPIC));
        return TopicBuilder
                .name(KafkaTopicNames.ATTIVITA_TOPIC.getName())
                .build();
    }

    @Bean
    public NewTopic attivitaListTopic(){

        log.info(String.format("Nome topic kafka: %s",KafkaTopicNames.ATTIVITA_LIST_TOPIC));
        return TopicBuilder
                .name(KafkaTopicNames.ATTIVITA_LIST_TOPIC.getName())
                .build();
    }
    */


    @Bean
    public NewTopics createTopics(){
        List<NewTopic> allTopics = new ArrayList<>();

        for(KafkaTopicNames topicName:KafkaTopicNames.values()){
            allTopics.add(
                    TopicBuilder.name(topicName.getName()).
                            partitions(1)
                            .replicas(1)
                            .build()
            );
        }


        NewTopic[] topicsArr = new NewTopic[allTopics.size()];

        allTopics.toArray(topicsArr);
        NewTopics newTopics = new NewTopics(topicsArr);
        return newTopics;
    }

}
