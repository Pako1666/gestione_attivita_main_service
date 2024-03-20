package it.gestore_attivita.gestore_attivita.rest;

import it.gestore_attivita.gestore_attivita.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GestoreAttivitaAdviceController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handle404Error(Exception e){

        return ResponseEntity
                .status(404)
                .body(
                    new ErrorDto(
                        HttpStatus.NOT_FOUND,
                        "Client Error",
                        e.getMessage()
                    )
                );
    }

    @ExceptionHandler({NullPointerException.class, RuntimeException.class})
    public ResponseEntity<ErrorDto> handle500Error(Exception e){

        return ResponseEntity
                .status(500)
                .body(
                        new ErrorDto(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Errore interno al server",
                                e.getMessage()
                        )
                );
    }


}
