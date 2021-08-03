package gg.cse.service.search;

import gg.cse.domain.RiotAPI;
import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class MatchHistoryServiceTest {
    @Mock
    RiotAPI riotAPI;

    @Mock
    SummonerDto summonerDto;

    @InjectMocks
    MatchHistoryService matchHistoryService;

    @Test
    void match_history() {
        String summonerName = "Hide on bush";
        List<String> matchIds = new LinkedList<>();
        matchIds.add("abc1");
        matchIds.add("abc2");
        matchIds.add("abc3");

        when(summonerDto.getPuuid()).thenReturn("some puuid");
        when(riotAPI.getSummonerWithName(any(String.class))).thenReturn(summonerDto);
        when(riotAPI.getMatchHistory(any(String.class))).thenReturn(matchIds);
        when(riotAPI.getMatchWithId(any(String.class))).thenReturn(new MatchDto());

        List<MatchDto> result = matchHistoryService.matchHistory(summonerName);
        assertEquals(result.size(), 3);
    }

    @Test
    void match_history_when_no_such_summoner() {
        String summonerName = "Not Existing Summoner Name 12345666";
        List<String> matchIds = new LinkedList<>();
        matchIds.add("abc1");
        matchIds.add("abc2");
        matchIds.add("abc3");

        when(riotAPI.getSummonerWithName(any(String.class))).thenReturn(null);  // returns null

        List<MatchDto> result = matchHistoryService.matchHistory(summonerName);
        assertNull(result);
    }
}