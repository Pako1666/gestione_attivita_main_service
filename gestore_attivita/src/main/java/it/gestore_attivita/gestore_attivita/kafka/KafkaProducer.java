package it.gestore_attivita.gestore_attivita.kafka;

import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaListSchema;
import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaRequestGenerated;
import it.gestore_attivita.gestore_attivita.kafka.avro.AttivitaRequestKey;
import it.gestore_attivita.gestore_attivita.kafka.config.KafkaTopicNames;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.AttivitaResponseDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.LavoraAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import it.gestore_attivita.gestore_attivita.ws.model.AttivitaRequestDto;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KafkaProducer {


    @Autowired
    private KafkaTemplate<AttivitaRequestKey, org.apache.avro.specific.SpecificRecordBase> kafkaTemplate;


    public void fetchAllAttivitas(List<AttivitaModel> attivitaList){

        AttivitaRequestKey key = new AttivitaRequestKey();
        key.setId(KafkaKeysEnum.FETCH_ALL_ATTIVITAS.name());
        List<AttivitaRequestGenerated> generatedList = attivitaList
                .stream()
                .map(
                        e->fromModelToGenerated(e)
                )
                .toList()
        ;

        AttivitaListSchema schema = AttivitaListSchema
                .newBuilder()
                .setAttivitas(generatedList)
                .build();


        kafkaTemplate.send(KafkaTopicNames.ATTIVITA_LIST_TOPIC.getName(),key,schema);
    }

    /*public void insertNewAttivita(AttivitaResponseDto att){
        AttivitaRequestGenerated generated = fromDtoToGenerated(att);

        AttivitaRequestKey key = AttivitaRequestKey
                .newBuilder()
                .setId(KafkaKeysEnum.INSERT_ATTIVITA.name())
                .build();
        kafkaTemplate.send(KafkaTopicNames.ATTIVITA_TOPIC.getName(),key,generated);
    }*/

    public void attivitaTopicProduce(KafkaKeysEnum keyEnum, AttivitaResponseDto dto){
        AttivitaRequestGenerated generated = fromDtoToGenerated(dto);
        AttivitaRequestKey key = AttivitaRequestKey
                .newBuilder()
                .setId(keyEnum.name())
                .build();

        kafkaTemplate.send(KafkaTopicNames.ATTIVITA_TOPIC.getName(),key,generated);
    }

    public void attivitaTopicProduce(KafkaKeysEnum keyEnum, AttivitaModel  model){
        AttivitaRequestGenerated generated = fromModelToGenerated(model);
        AttivitaRequestKey key = AttivitaRequestKey
                .newBuilder()
                .setId(keyEnum.name())
                .build();

        kafkaTemplate.send(KafkaTopicNames.ATTIVITA_TOPIC.getName(),key,generated);
    }


    private AttivitaRequestGenerated fromModelToGenerated(AttivitaModel model){
        AttivitaRequestGenerated generated = new AttivitaRequestGenerated();
        generated.setId(model.getId());
        generated.setAlias(model.getAlias());
        generated.setLavorata(model.getLavorata().equals("SI"));
        generated.setAttivitaPadre(model.getAttivitaPadre());
        return generated;
    }

    private AttivitaRequestGenerated fromDtoToGenerated(AttivitaResponseDto dto){
        AttivitaRequestGenerated generated = new AttivitaRequestGenerated();
        generated.setId(dto.getId());
        generated.setAlias(dto.getAlias());
        generated.setLavorata(dto.getLavorata());
        generated.setAttivitaPadre(dto.getAttivitaPadre());
        return generated;
    }




}
