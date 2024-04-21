package it.gestore_attivita.gestore_attivita.rest.attività.service;

import it.gestore_attivita.gestore_attivita.exception.NotFoundException;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.*;

import java.util.List;

public interface AttivitaService {

    AttivitaResponseDto getAttivita(Long id) throws Exception;

    PaginatorResponseDto getPaginatorInfo(Integer num);

    List<AttivitaResponseDto> getAllAttivita(PageAttivitaDto pag);

    //List<AttivitaResponseDto> getAllAttivitaByAttivitaPadre(Long idFather) throws NotFoundException;

    AttivitaResponseDto insertAttivita(InsertAttivitaRequestDto req) throws NotFoundException;

    VerificaAttivitaDto verificaAttivita(Long idAttivita) throws Exception;

    LavoraAttivitaDto lavoraAttivita(Long idAttivita) throws Exception;

}
