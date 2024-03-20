package it.gestore_attivita.gestore_attivita.ws;

import it.gestore_attivita.gestore_attivita.ws.model.AttivitaRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;

@Service
@Slf4j
public class WebServiceConfig {

    private RestTemplate restTemplate;
    private String baseUrl = "http://localhost:5050/v1/logs-api/";

    private static WebServiceConfig instance;

    private WebServiceConfig(){
        //HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
       // httpRequestFactory.setConnectionRequestTimeout(5000);
        //restTemplate = new RestTemplate();
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();

    }

    public static WebServiceConfig getInstance(){
        if(instance==null)
            instance = new WebServiceConfig();

        return instance;
    }

    public <REQ,RES> RES doPost(String resource, REQ  req,Class<RES> resCls){

        HttpEntity<REQ> request = new HttpEntity<REQ>(req);

        log.info(this.baseUrl + "/" + resource);

        RES body = this.restTemplate.postForObject(
                this.baseUrl + "/" + resource,
                request, resCls
        );
        return body;
    }

    public <T> T doGet(String resource,Class<T> cls){
        //HttpEntity<AttivitaRequestDto> request = new HttpEntity<AttivitaRequestDto>(req);

        T body = this.restTemplate.getForObject(baseUrl+"/"+resource, cls);
        return body;
    }

    public <T> T doGet(String resource,Class<T> cls, String pathVariable){
        //HttpEntity<AttivitaRequestDto> request = new HttpEntity<AttivitaRequestDto>(req);
//        T body = this.restTemplate.getForObject(baseUrl+"/"+resource,
//                cls,pathVariable);
        String uri = String.format("%s%s/%s",baseUrl,resource,pathVariable);
        log.info(uri);
        return this.restTemplate.getForObject(uri, cls);
    }

    public <T> T doGet(String resource, Class<T>cls, Map<String,Object> query){
        String uri = String.format("%s/%s?",baseUrl,resource);

        for(String key: query.keySet()){
            uri = uri.concat(
                    String.format("%s=%o&",key,query.get(key))
            );
        }


        return this.restTemplate.getForObject(uri, cls);
    }






}
