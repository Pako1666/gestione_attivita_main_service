package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginazioneAttivitaResponseDto {
    private List<AttivitaResponseDto> items;
}
