package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.Data;

@Data
public class InsertAttivitaRequestDto {
    public String alias;
    public String lavorata;
    public Long attivitaPadre;
}
