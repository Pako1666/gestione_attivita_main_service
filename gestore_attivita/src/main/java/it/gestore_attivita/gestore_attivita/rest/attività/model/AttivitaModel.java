package it.gestore_attivita.gestore_attivita.rest.attività.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attivita")
public class AttivitaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "alias")
    public String alias;

    @Column(name = "lavorata",columnDefinition="char(2)")
    public String lavorata;

    @Column(name = "attiv" +
            "ita_padre")
    public Long attivitaPadre;


}
