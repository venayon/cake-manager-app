package com.waracle.cakemanager;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemanager.entity.CakeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class CakeMngrLoader implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(CakeMngrLoader.class);

    @Value("${cake.repo.url}")
    private String repoUrl;

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private ExtClientJson extClientJson;


    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("downloading cake json");
        String json = extClientJson.getCakes(repoUrl);
        if (StringUtils.hasText(json)) {
            try {
                Collection<CakeEntity> entities = parse(json);
                if (!CollectionUtils.isEmpty(entities)) {
                    LOGGER.info("adding cake entity {}", entities);
                    cakeRepository.save(entities);
                    LOGGER.info("Cake entities persisted successfully");
                }
            } catch (IOException e) {
                LOGGER.error("Bad response received from Cake repo service ", e);
            }
        }
    }

    protected Collection<CakeEntity> parse(String jsonString) throws IOException {
        JsonParser parser = new JsonFactory().createParser(jsonString);
        if (JsonToken.START_ARRAY != parser.nextToken()) {
            throw new IOException("bad token");
        }
        // using map to form unique cake set
        final Map<String, CakeEntity> cakeEntityMap = new HashMap<>();
        JsonToken nextToken = parser.nextToken();
        while (nextToken == JsonToken.START_OBJECT) {
            LOGGER.info("creating cake entity");
            CakeEntity cake = new CakeEntity();
            LOGGER.info(parser.nextFieldName());
            cake.setTitle(parser.nextTextValue());
            LOGGER.info(parser.nextFieldName());
            cake.setDescription(parser.nextTextValue());
            LOGGER.info(parser.nextFieldName());
            cake.setImage(parser.nextTextValue());
            cakeEntityMap.put(cake.getTitle(), cake);
            nextToken = parser.nextToken();
            LOGGER.info(nextToken.toString());
            nextToken = parser.nextToken();
            LOGGER.info(nextToken.toString());
        }
        return cakeEntityMap.values();
    }
}

