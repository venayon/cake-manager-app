package com.waracle.cakemanager;

import com.waracle.cakemanager.entity.CakeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Service
public class ExtClientJson {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtClientJson.class);
    @Autowired
    private RestTemplate jsonRestTemplate;

    @PostConstruct
    public void init() {


    }

    public List<CakeEntity> getCakeList(String uri) {
        LOGGER.info("Fetching cakes from external service");
        ResponseEntity<List<CakeEntity>> cakeEntities = jsonRestTemplate.exchange("https://bitpay.com/api/rates",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CakeEntity>>() {

                });
        return cakeEntities.getBody();
    }

    public String getCakes(String uri) {
        LOGGER.info("Fetching cakes from external service");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> result = jsonRestTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        LOGGER.info("Fetching cakes from external service : {} ", result.getBody());
        return result.getBody();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setSupportedMediaTypes(singletonList(TEXT_PLAIN));
        restTemplate.getMessageConverters().add(jackson2HttpMessageConverter);
        return restTemplate;
    }


}
