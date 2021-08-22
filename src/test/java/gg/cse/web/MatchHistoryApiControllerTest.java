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
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MatchHistoryApiController.class)
public class MatchHistoryApiControllerTest {
    @MockBean
    MatchHistoryService matchHistoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_match_history() throws Exception {
        List<MatchDto> matchDtos = new LinkedList<>();
        for(int i = 0; i < 20; i++) {
            matchDtos.add(new MatchDto());
        }
        when(matchHistoryService.getMatchHistory(any(String.class))).thenReturn(Optional.of(matchDtos));

        String summonerName = "Hide on bush";

        mockMvc.perform(get("/api/v1/match_history/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[19].info").isEmpty());
    }

    @Test
    public void get_match_history_when_no_such_summoner() throws Exception {
        String notExistingSummoner = "not_existing_summoner_name";
        when(matchHistoryService.getMatchHistory(notExistingSummoner)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/match_history/" + notExistingSummoner))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void update_match_history() throws Exception {
        when(matchHistoryService.updateMatchHistory(any(String.class))).thenReturn(true);

        String summonerName = "Hide on bush";

        mockMvc.perform(post("/api/v1/match_history/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void update_match_history_when_no_such_summoner() throws Exception {
        when(matchHistoryService.updateMatchHistory(any(String.class))).thenReturn(false);

        String summonerName = "not_existing_summoner_name";

        mockMvc.perform(post("/api/v1/match_history/" + summonerName))
                .andExpect(status().is(404));
    }
}
