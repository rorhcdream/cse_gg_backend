package gg.cse.domain;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RiotAPI {
    @Value("${riot-api-key}")
    private String api_key;
    private final String base_url = "https://%s.api.riotgames.com";
    private final String kr_region = "kr";
    private final String asia_region = "asia";

    public SummonerDto getSummonerWithName(String summonerName) {
        final String path = "/lol/summoner/v4/summoners/by-name/";
        String url = String.format(base_url, kr_region) + path + summonerName;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", api_key);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<SummonerDto> response =
                restTemplate.exchange(url, HttpMethod.GET, request, SummonerDto.class);
        return response.getBody();
    }

    public List<String> getMatchHistory(String puuid) {
        final String path = "/lol/match/v5/matches/by-puuid/%s/ids";
        String url = String.format(base_url, asia_region) + String.format(path, puuid);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", api_key);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<List<String>> response =
                restTemplate.exchange(url, HttpMethod.GET, request,
                        new ParameterizedTypeReference<List<String>>() {});
        return response.getBody();
    }

    public MatchDto getMatchWithId(String matchId) {
        final String path = "/lol/match/v5/matches/";
        String url = String.format(base_url, asia_region) + path + matchId;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Riot-Token", api_key);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<MatchDto> response =
                restTemplate.exchange(url, HttpMethod.GET, request, MatchDto.class);
        return response.getBody();
    }
}
