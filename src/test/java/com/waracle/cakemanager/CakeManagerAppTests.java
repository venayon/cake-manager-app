package com.waracle.cakemanager;

import com.waracle.cakemanager.ext.ExtClientJson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeManagerAppTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ExtClientJson extClientJson;

    private String serviceUri;

    @Before
    public void setup() throws IOException {
        serviceUri = String.format("http://localhost:%s/", port);
    }

    @Test
    public void testCakesUri() {
        System.out.println(serviceUri);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(serviceUri, GET, null, String.class);
        Assert.assertTrue(OK == responseEntity.getStatusCode());
    }

}
