package it.gestore_attivita.gestore_attivita.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttivitaRequestDto {
    public Long id;
    public String alias;
    public Boolean lavorata;
    public Long attivitaPadre;
}

