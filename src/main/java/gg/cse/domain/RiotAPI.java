package gg.cse.domain;

import gg.cse.dto.riotDto.SummonerDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RiotAPI {
    public SummonerDTO getSummonerWithName(String summonerName) {
        final String uri = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
        final String api_key = "<riot-api-key>";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", api_key);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<SummonerDTO> response =
                restTemplate.exchange(uri + summonerName, HttpMethod.GET, request, SummonerDTO.class);
        return response.getBody();

    }
}
