package it.gestore_attivita.gestore_attivita.rest.log;

import it.gestore_attivita.gestore_attivita.rest.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.mapping.Join;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Component
public class LogHandler {

    private final Logger log = getLogger(this.getClass());

    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.attività" +
            ".service.AttivitaService.getAttivita(Long)" +
            ")")
    public void logGetActivityFromDB(JoinPoint joinPoint){
       log.info(String.format("Recuperando l'attivita #%d dal db",joinPoint.getArgs()[0]));
    }

    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.attività" +
            ".service.AttivitaService.verificaAttivita(Long)" +
            ")")
    public void logVerificaAttività(JoinPoint joinPoint){
        log.info(String.format("Verificando se l'attivita #%d sia lavorabile, oppure no",joinPoint.getArgs()[0]));
    }

    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.attività" +
            ".service.AttivitaService.lavoraAttivita(Long)" +
            ")")
    public void logLavoraAttivita(JoinPoint joinPoint){
        log.info(String.format("Lavorando l'attivita #%d ",joinPoint.getArgs()[0]));
    }

    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.attività" +
            ".service.AttivitaService.insertAttivita(*)" +
            ")")
    public void logInserimentoAttività(JoinPoint jp){
        log.info(String.format("Insernedo una nuova attività nel db"));
        log.info(jp.getArgs()[0].toString());
    }


    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.GestoreAttivitaAdviceController.*(*)" +
            ")")
    public void logErrors(JoinPoint jp){
        log.info("Exception occurs");
        Exception ex = (Exception) (jp.getArgs()[0]);
        log.info(ex.getMessage());
    }

    @Before("execution(" +
            "public * it.gestore_attivita.gestore_attivita.rest.GestoreAttivitaAdviceController.*(*)" +
            ")")
    public ErrorDto prova(JoinPoint jp){
        log.error("erro dto di seguito: ");
        return new ErrorDto(
                HttpStatus.PROCESSING,
                "INFO",
                "PROVA"
        );
    }
}
