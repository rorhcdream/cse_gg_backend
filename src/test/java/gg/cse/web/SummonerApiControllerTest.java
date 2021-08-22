package gg.cse.web;

import gg.cse.domain.Summoner;
import gg.cse.dto.riotDto.SummonerDto;
import gg.cse.service.search.SummonerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummonerApiController.class)
class SummonerApiControllerTest {
    @MockBean
    SummonerService summonerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_summoner_test() throws Exception {
        String summonerName = "Hide on bush";
        SummonerDto summonerDto = SummonerDto.builder()
                .name(summonerName)
                .puuid("puuid")
                .build();
        when(summonerService.getSummoner(summonerName))
                .thenReturn(Optional.of(summonerDto));

        mockMvc.perform(get("/api/v1/summoner/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(summonerDto));
    }

    @Test
    public void get_summoner_when_no_such_summoner() throws Exception {
        String summonerName = "not_existing_summoner_name";
        when(summonerService.getSummoner(summonerName))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/summoner/" + summonerName))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void update_summoner_test() throws Exception {
        String summonerName = "Hide on bush";
        when(summonerService.updateSummoner(summonerName)).thenReturn(Optional.of(new Summoner()));

        mockMvc.perform(post("/api/v1/summoner/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void update_summoner_when_no_such_summoner() throws Exception {
        String summonerName = "not_existing_summoner_name";
        when(summonerService.updateSummoner(summonerName)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/summoner/" + summonerName))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$").doesNotExist());
    }
}