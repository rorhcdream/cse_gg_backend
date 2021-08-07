package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.service.search.MatchHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MatchHistoryApiController.class)
public class MatchHistoryApiControllerTest {
    @MockBean
    MatchHistoryService matchHistoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void search_test() throws Exception {
        List<MatchDto> matchDtos = new LinkedList<>();
        for(int i = 0; i < 20; i++) {
            matchDtos.add(new MatchDto());
        }
        when(matchHistoryService.matchHistory(any(String.class))).thenReturn(matchDtos);

        String summonerName = "Hide on bush";

        mockMvc.perform(get("/api/v1/match_history/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[19].info").isEmpty());
    }

    @Test
    public void search_not_existing_summoner() throws Exception {
        String notExistingSummoner = "not_existing_summoner_name";
        when(matchHistoryService.matchHistory(notExistingSummoner)).thenReturn(null);

        mockMvc.perform(get("/api/v1/match_history/" + notExistingSummoner))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$").doesNotExist());
    }
}
