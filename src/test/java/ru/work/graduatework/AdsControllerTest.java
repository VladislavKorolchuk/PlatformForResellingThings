package ru.work.graduatework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.controller.AdsController;
import ru.work.graduatework.dto.AdsDto;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdsControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private AdsController adsController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        Assertions.assertThat(adsController).isNotNull();
    }

    @Test
    public void getAllAdsTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads", String.class))
                .isNotNull();
    }


}
