package it.gestore_attivita.gestore_attivita.rest.attività.service.impl;


import it.gestore_attivita.gestore_attivita.exception.NotFoundException;
import it.gestore_attivita.gestore_attivita.kafka.KafkaProducer;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.AttivitaResponseDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.InsertAttivitaRequestDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.LavoraAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.VerificaAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaRepository;
import it.gestore_attivita.gestore_attivita.rest.attività.service.AttivitaService;
import it.gestore_attivita.gestore_attivita.ws.LogEndpoints;
import it.gestore_attivita.gestore_attivita.ws.WebServiceConfig;
import it.gestore_attivita.gestore_attivita.ws.model.AttivitaRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttivitaServiceImpl implements AttivitaService {


    @Autowired
    private AttivitaRepository attivitaRepository;

    @Autowired
    private WebServiceConfig webServiceConfig;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public AttivitaResponseDto getAttivita(Long id) throws Exception {


        //recupero dal DB l'attività

        AttivitaModel attivitaModel = attivitaRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("Attività #%d non trovata", id.intValue())
                )
        );


        webServiceConfig.doGet(LogEndpoints.FETCH_ATTIVITA,Boolean.class,id+"");

        //restituisco il dto
        return fromModelToDto(attivitaModel);
    }

    @Override
    public List<AttivitaResponseDto> getAllAttivita() {

        List<AttivitaResponseDto> attivitas = attivitaRepository.findAll()
                .stream()
                .map(a -> fromModelToDto(a))
                .collect(Collectors.toList());

        //webServiceConfig.doGet(LogEndpoints.FETCH_ALL_ATTIVITA, Boolean.class);


        kafkaProducer.fetchAllAttivitas(fromDtoToModel(attivitas.size()>0?attivitas.get(0):new AttivitaResponseDto(
                1L,"a1",true,null
        )));
        return attivitas;
    }


    private List<AttivitaResponseDto> getAllAttivitaByAttivitaPadre(Long idFather) throws NotFoundException {

        List<Long> listId = attivitaRepository.findAllIds();
        Set<AttivitaModel> attivitas = new HashSet<>();

        if(!listId.contains(idFather)){
            throw new NotFoundException(String.format("Attivia n° %d non esiste",idFather));
        }

        /*attivitas = repository.findByAttivitaPadreContains(
                repository.findAll().stream().filter(
                        a->a.getAttivitaPadre()!=null
                )
                        .map(a->a.getAttivitaPadre())
                        .toList()
        );*/

        AttivitaModel attivitaPadre = attivitaRepository.findById(idFather).get();
        attivitas.add(attivitaPadre);

        while(attivitaPadre.getAttivitaPadre()!=null||
                (attivitaPadre.getAttivitaPadre()!=null&&attivitaPadre.getLavorata().equals("SI"))){
            attivitaPadre = attivitaRepository
                    .findById(attivitaPadre.getAttivitaPadre())
                    .orElseThrow(()->new NotFoundException("Nessun attività trovata"));

            attivitas.add(attivitaPadre);

        }


        return attivitas
                .stream()
                .map(
                        a -> fromModelToDto(a)
                )
                .collect(Collectors.toList());
    }


    @Override
    public AttivitaResponseDto insertAttivita(InsertAttivitaRequestDto req) {
        AttivitaModel model = new AttivitaModel();
        model.setAttivitaPadre(req.getAttivitaPadre());
        model.setAlias(req.getAlias());
        model.setLavorata(req.getLavorata());

        AttivitaResponseDto dto = fromModelToDto(attivitaRepository.save(model));

        AttivitaRequestDto att = AttivitaRequestDto
                .builder()
                .attivitaPadre(dto.getAttivitaPadre())
                .alias(dto.getAlias())
                .id(dto.getId())
                .lavorata(dto.getLavorata())
                .build();

        log.info("Attività passata al microservizio:");
        log.info(att.toString());

        webServiceConfig.doPost("insert-attivita", att, Boolean.class);
        return dto;

    }


    private Boolean checkLavorabile(AttivitaModel attivita) throws NotFoundException {
        Boolean lavorabile = false;
        List<AttivitaResponseDto> attivitaPadri = new ArrayList<>();

        /**
         * codice per verificare la lavorabilità
         *
         *
         * */
        if(attivita.getAttivitaPadre()==null){
            lavorabile = attivita.getLavorata().equals("SI");
        }

        else{
            attivitaPadri = this.getAllAttivitaByAttivitaPadre(attivita.getAttivitaPadre());
            for(AttivitaResponseDto att : attivitaPadri){

                if(att.getLavorata()){
                    lavorabile = true;
                    //ciclo per verififcare i padri dei padri
                }

                else{
                    lavorabile = false;
                    break;
                }
            }
        }

        return lavorabile;
    }

    @Override
    public VerificaAttivitaDto verificaAttivita(Long idAttivita) throws Exception {
        VerificaAttivitaDto resp = new VerificaAttivitaDto();
        Boolean lavorabile = false;
        AttivitaResponseDto attivita = this.getAttivita(idAttivita);

        resp.setId(attivita.getId());

        lavorabile = checkLavorabile(fromDtoToModel(attivita));
        resp.setLavorabile(lavorabile);

        AttivitaRequestDto att = new AttivitaRequestDto();
        att.setAlias(attivita.getAlias());
        att.setId(attivita.getId());
        att.setAttivitaPadre(attivita.getAttivitaPadre());
        att.setLavorata(attivita.getLavorata());
        webServiceConfig.doPost("verifica-attivita",att,Boolean.class);
        return resp;
    }

    @Override
    public LavoraAttivitaDto lavoraAttivita(Long idAttivita) throws Exception {
        LavoraAttivitaDto resp = new LavoraAttivitaDto();
        AttivitaModel attivitaM = new AttivitaModel();
        resp.setIdAttivita(idAttivita);

        if(checkLavorabile(fromDtoToModel(getAttivita(idAttivita)))){

            attivitaM = fromDtoToModel(this.getAttivita(idAttivita));
            if(attivitaM.getLavorata().equals("NO")){
                attivitaM.setLavorata("SI");
                attivitaRepository.save(attivitaM);
                resp.setMessage("Attività lavorata con successo");
            }

            else {
                resp.setMessage("Attività già lavorata");
            }

            resp.setLavorata(true);

            AttivitaRequestDto att = new AttivitaRequestDto();
            att.setAlias(attivitaM.getAlias());
            att.setId(attivitaM.getId());
            att.setAttivitaPadre(attivitaM.getAttivitaPadre());
            att.setLavorata(attivitaM.getLavorata().equals("SI"));
            webServiceConfig.doPost(LogEndpoints.LAVORA_ATTIVITA,att,Boolean.class);

        }
        else {
            resp.setMessage("Impossibile lavorare questa attività. Controlla che quelle precedenti siano state lavorate");
            resp.setLavorata(false);
        }



        return resp;
    }

    private AttivitaModel fromDtoToModel(AttivitaResponseDto a){
        AttivitaModel model = new AttivitaModel();
        model.setId(a.getId());
        model.setAttivitaPadre(a.getAttivitaPadre());
        model.setLavorata(a.getLavorata()?"SI":"NO");
        model.setAlias(a.getAlias());

        return model;
    }
    private AttivitaResponseDto fromModelToDto(AttivitaModel model){
        return AttivitaResponseDto
                .builder()
                .id(model.getId())
                .alias(model.getAlias())
                .attivitaPadre(model.getAttivitaPadre())
                .lavorata(model.getLavorata().equals("SI"))
                .build();
    }
}
