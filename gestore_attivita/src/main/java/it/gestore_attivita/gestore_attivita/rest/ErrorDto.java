package it.gestore_attivita.gestore_attivita.rest;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ToString
public class ErrorDto {

    @Getter
    @Setter
    private HttpStatus status;

    @Getter
    @Setter
    private String message;

    @Getter
    private final LocalDateTime timestamp;

    @Getter
    @Setter
    private String description;

    public ErrorDto(){
        timestamp = LocalDateTime.now();
    }

    public ErrorDto(HttpStatus status, String message, String description){
        this.status = status;
        this.message = message;
        this.description = description;
        timestamp = LocalDateTime.now();
    }

}
