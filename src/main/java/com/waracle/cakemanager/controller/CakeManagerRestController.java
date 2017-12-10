package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.CakeRepository;
import com.waracle.cakemanager.entity.CakeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/cakes")
public class CakeManagerRestController {

    @Autowired
    private CakeRepository cakeRepository;

    @RequestMapping(method = GET, value = "")
    public Iterable<CakeEntity> getAllCakes() {
        return cakeRepository.findAll();
    }

    @RequestMapping(value = "", method = POST)
    public ResponseEntity<String> addCakes(@RequestBody CakeEntity cake) {
        if (nonNull(cake) && StringUtils.hasText(cake.getTitle())) {
            cakeRepository.save(cake);
            return ResponseEntity.status(CREATED).build();
        }
        return ResponseEntity.status(NO_CONTENT).build();
    }

}
