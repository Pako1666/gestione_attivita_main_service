package it.gestore_attivita.gestore_attivita.rest.attività.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttivitaRepository extends JpaRepository<AttivitaModel,Long> {

    List<AttivitaModel> findByAttivitaPadre(Long attivitaPadre);

    @Query("SELECT a.id FROM AttivitaModel a")
    List<Long> findAllIds();

}
