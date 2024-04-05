package it.gestore_attivita.gestore_attivita.kafka;

import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaRequestGenerated;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import org.apache.coyote.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    @Value("${kafka.topic-name}")
    private String kafkaTopic;

    @Autowired
    private KafkaTemplate<String,AttivitaRequestGenerated> kafkaTemplate;


    public void fetchAllAttivitas(AttivitaModel attivitaModel){
        kafkaTemplate.send(kafkaTopic,fromModelToGenerated(attivitaModel));
    }

    private AttivitaRequestGenerated fromModelToGenerated(AttivitaModel model){
        AttivitaRequestGenerated generated = new AttivitaRequestGenerated();
        generated.setId(model.getId());
        generated.setAlias(model.getAlias());
        generated.setLavorata(model.getLavorata().equals("SI"));
        generated.setAttivitaPadre(model.getAttivitaPadre());
        return generated;
    }

}
