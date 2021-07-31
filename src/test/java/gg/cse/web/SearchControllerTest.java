package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void search_test() throws Exception {
        String summonerName = "Hide on bush";
        String url = "http://localhost:" + port + "/api/v1/search/" + summonerName;

        List<MatchDto> matchDtos =
                restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<MatchDto>>() {}
                ).getBody();
        assertNotNull(matchDtos);
        assertEquals(matchDtos.size(), 20);
    }
}
