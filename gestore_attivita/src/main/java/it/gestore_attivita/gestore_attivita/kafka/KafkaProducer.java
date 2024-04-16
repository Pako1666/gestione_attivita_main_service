package it.gestore_attivita.gestore_attivita.kafka;

import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaRequestKey;
import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaRequestValue;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {

    @Value("${kafka.topic-name}")
    private String kafkaTopic;

    @Autowired
    private KafkaTemplate<AttivitaRequestKey, AttivitaRequestValue> kafkaTemplate;


    public void fetchAllAttivitas(AttivitaModel attivitaModel){
        AttivitaRequestValue generated = fromModelToGenerated(attivitaModel);
        AttivitaRequestKey key = new AttivitaRequestKey();
        key.setId("ciao");
        kafkaTemplate.send(kafkaTopic,key,generated);
    }




    private AttivitaRequestValue fromModelToGenerated(AttivitaModel model){
        AttivitaRequestValue generated = new AttivitaRequestValue();
        generated.setId(model.getId());
        generated.setAlias(model.getAlias());
        generated.setLavorata(model.getLavorata().equals("SI"));
        generated.setAttivitaPadre(model.getAttivitaPadre());
        return generated;
    }

}
