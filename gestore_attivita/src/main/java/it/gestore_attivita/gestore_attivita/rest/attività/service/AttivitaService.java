package it.gestore_attivita.gestore_attivita.rest.attività.service;

import it.gestore_attivita.gestore_attivita.exception.NotFoundException;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.AttivitaResponseDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.InsertAttivitaRequestDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.LavoraAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.VerificaAttivitaDto;

import java.util.List;

public interface AttivitaService {

    AttivitaResponseDto getAttivita(Long id) throws Exception;

    List<AttivitaResponseDto> getAllAttivita();

    //List<AttivitaResponseDto> getAllAttivitaByAttivitaPadre(Long idFather) throws NotFoundException;

    AttivitaResponseDto insertAttivita(InsertAttivitaRequestDto req) throws NotFoundException;

    VerificaAttivitaDto verificaAttivita(Long idAttivita) throws Exception;

    LavoraAttivitaDto lavoraAttivita(Long idAttivita) throws Exception;

}
