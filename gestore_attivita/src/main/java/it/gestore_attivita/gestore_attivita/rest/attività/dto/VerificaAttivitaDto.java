package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificaAttivitaDto {

    public Long id;
    private Boolean lavorabile;

}
