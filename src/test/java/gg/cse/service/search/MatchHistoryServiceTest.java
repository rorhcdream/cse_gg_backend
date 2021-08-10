package gg.cse.service.search;

import gg.cse.domain.*;
import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class MatchHistoryServiceTest {
    @Mock
    SummonerRepository summonerRepository;

    @Mock
    MatchRepository matchRepository;

    @InjectMocks
    MatchHistoryService matchHistoryService;

    @Test
    void match_history() {
        String summonerName = "Hide on bush";
        Match match = Match.builder()
                .matchId("match_id")
                .gameCreation(123L)
                .participants(List.of(Participant.builder()
                        .summonerName(summonerName)
                        .build()))
                .build();
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .matches(List.of(match, match, match))
                .build();

        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.of(summoner));

        List<MatchDto> result = matchHistoryService.matchHistory(summonerName);
        assertEquals(3, result.size());
        assertEquals(summonerName, result.get(0).getInfo().getParticipants().get(0).getSummonerName());
    }

    @Test
    void match_history_when_no_such_summoner() {
        String summonerName = "Not Existing Summoner Name 123";

        when(summonerRepository.findByName(any(String.class))).thenReturn(Optional.empty());  // returns null

        List<MatchDto> result = matchHistoryService.matchHistory(summonerName);
        assertNull(result);
    }
}