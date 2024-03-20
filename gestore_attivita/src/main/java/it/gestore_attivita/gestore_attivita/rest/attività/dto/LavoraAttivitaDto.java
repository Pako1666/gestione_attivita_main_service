package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LavoraAttivitaDto {
    private Long idAttivita;
    private Boolean lavorata;
    private String message;
}
