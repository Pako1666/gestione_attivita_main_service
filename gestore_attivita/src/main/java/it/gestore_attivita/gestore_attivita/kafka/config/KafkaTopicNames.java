package it.gestore_attivita.gestore_attivita.kafka.config;

import lombok.Getter;

@Getter
public enum KafkaTopicNames {

    ATTIVITA_LIST_TOPIC("attivita_list_topic"),
    ATTIVITA_TOPIC("attivita_topic");

    private String name;

    KafkaTopicNames(String name){
        this.name=name;
    }


}
