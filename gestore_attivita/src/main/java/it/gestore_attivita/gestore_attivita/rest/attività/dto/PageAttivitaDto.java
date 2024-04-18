package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageAttivitaDto {
    private Integer page;
    private Integer items;
}
