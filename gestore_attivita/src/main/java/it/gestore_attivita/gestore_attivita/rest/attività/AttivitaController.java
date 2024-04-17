package it.gestore_attivita.gestore_attivita.rest.attività;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gestore_attivita.gestore_attivita.exception.NotFoundException;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.AttivitaResponseDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.InsertAttivitaRequestDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.LavoraAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.dto.VerificaAttivitaDto;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import it.gestore_attivita.gestore_attivita.rest.attività.service.impl.AttivitaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Attività")
@RestController
@RequestMapping("v1/attivita-api/")
@CrossOrigin(origins = "*")
public class AttivitaController {

    @Autowired
    private AttivitaServiceImpl attivitaService;


    @Operation(summary="returns all Attivita entities stored into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ottieni la lista di Attività",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AttivitaModel.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request!"),
            @ApiResponse(responseCode = "404", description = "Nessuna attività censita") })
    @GetMapping("all-attivita/")
    public ResponseEntity<List<AttivitaResponseDto>> attivitaList(){

        return ResponseEntity.ok().body(attivitaService.getAllAttivita(null,null));
    }

    @Operation(summary="insert Attivita entity into database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Attivita entity stored",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request!"),
    })
    @PostMapping("insert/")
    public ResponseEntity<AttivitaResponseDto> insertAttivita(@RequestBody InsertAttivitaRequestDto req) throws NotFoundException {
        return ResponseEntity.ok().body(
                attivitaService.insertAttivita(req)
        );
    }

    @Operation(summary="get Attivita by it's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get Attivita entity by ID",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request!"),
            @ApiResponse(responseCode = "404", description = "Attività non trovata!"),
    })
    @GetMapping("attivita/{id}")
    public ResponseEntity<AttivitaResponseDto> getAttivitaById(@PathVariable("id") Long id)
            throws Exception {
        return ResponseEntity.ok().body(attivitaService.getAttivita(id));
    }


    @Operation(summary="verifica se un' Attivita è lavorabile oppure no")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "verifica se un' Attivita è lavorabile oppure no, " +
                    "verifocando che le sue attività padre siano lavorabili",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request!"),
            @ApiResponse(responseCode = "404", description = "Attività non trovata!"),
    })
    @GetMapping("attivita/verifica/{idAttivita}")
    public ResponseEntity<VerificaAttivitaDto> checkLavorabilitaAttivita(
            @PathVariable("idAttivita") Long idAttPadre)
            throws Exception {
        return ResponseEntity.ok().body(
                attivitaService.verificaAttivita(idAttPadre)
        );
    }


    @Operation(summary="verifica se un' Attivita è lavorabile oppure no")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "verifica se un' Attivita è lavorabile oppure no, " +
                    "verifocando che le sue attività padre siano lavorabili",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request!"),
            @ApiResponse(responseCode = "404", description = "Attività non trovata!"),
    })
    @PutMapping("/attivita/lavora/{idAttivita}")
    public ResponseEntity<LavoraAttivitaDto> lavoraAttivita(@PathVariable("idAttivita") Long idAtt)
            throws Exception {
        return ResponseEntity.ok().body(attivitaService.lavoraAttivita(idAtt));
    }

}
