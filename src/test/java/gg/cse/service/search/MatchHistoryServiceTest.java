package gg.cse.service.search;

import gg.cse.domain.*;
import gg.cse.dto.riotDto.MatchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchHistoryServiceTest {
    @Mock
    SummonerRepository summonerRepository;

    @Mock
    MatchRepository matchRepository;

    @Mock
    RiotAPI riotAPI;

    @InjectMocks
    MatchHistoryService matchHistoryService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(matchHistoryService, "self", matchHistoryService);
    }

    @Test
    void get_match_history() {
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

        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.of(summoner));

        Optional<List<MatchDto>> result = matchHistoryService.getMatchHistory(summonerName);
        assertEquals(3, result.get().size());
        assertEquals(summonerName, result.get().get(0).getInfo().getParticipants().get(0).getSummonerName());
    }

    @Test
    void get_match_history_when_no_match_in_database_yet() {
        String summonerName = "Hide on bush";
        String summonerPuuid = "puuid";
        Match match = Match.builder()
                .matchId("match_id")
                .gameCreation(123L)
                .participants(List.of(Participant.builder()
                        .summonerName(summonerName)
                        .build()))
                .build();
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .puuid(summonerPuuid)
                .build();

        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.of(summoner));
        when(riotAPI.getMatchHistory(summonerPuuid)).thenReturn(List.of("match_id"));
        when(riotAPI.getMatchWithId("match_id")).thenReturn(MatchDto.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(null);

        Optional<List<MatchDto>> result = matchHistoryService.getMatchHistory(summonerName);
        verify(matchRepository).save(any(Match.class));
        assertEquals(1, result.get().size());
        assertEquals(summonerName, result.get().get(0).getInfo().getParticipants().get(0).getSummonerName());
    }

    @Test
    void get_match_history_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";

        when(summonerRepository.findByNameIgnoreCase(any(String.class))).thenReturn(Optional.empty());  // returns null

        Optional<List<MatchDto>> result = matchHistoryService.getMatchHistory(summonerName);
        assertTrue(result.isEmpty());
    }

    @Test
    void update_match_history() {
        String summonerName = "Hide on bush";
        String summonerPuuid = "puuid";
        Match match = Match.builder()
                .matchId("match_id")
                .gameCreation(123L)
                .participants(List.of(Participant.builder()
                        .summonerName(summonerName)
                        .build()))
                .build();
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .puuid(summonerPuuid)
                .matches(new ArrayList<>(List.of(match, match, match)))
                .build();

        Match newEarlierMatch = Match.builder()
                .matchId("match_id1")
                .gameCreation(99L)
                .participants(List.of(Participant.builder()
                        .summonerName(summonerName)
                        .build()))
                .build();
        Match newLaterMatch = Match.builder()
                .matchId("match_id2")
                .gameCreation(225L)
                .participants(List.of(Participant.builder()
                        .summonerName(summonerName)
                        .build()))
                .build();
        List<String> matchIds = List.of("match_id1", "match_id2");

        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.of(summoner));
        when(riotAPI.getMatchHistory(summonerPuuid)).thenReturn(List.of("match_id1", "match_id2"));
        when(riotAPI.getMatchWithId("match_id1")).thenReturn(MatchDto.of(newEarlierMatch));
        when(riotAPI.getMatchWithId("match_id2")).thenReturn(MatchDto.of(newLaterMatch));
        when(matchRepository.save(any(Match.class))).thenReturn(null);

        boolean updated = matchHistoryService.updateMatchHistory(summonerName);

        // match earlier than last saved match should not be saved
        verify(matchRepository, times(0)).save(newEarlierMatch);
        verify(matchRepository, times(1)).save(newLaterMatch);
        assertEquals(4, summoner.getMatches().size());
        assertEquals(newLaterMatch, summoner.getMatches().get(3));
        assertTrue(updated);
    }

    @Test
    void update_match_history_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.empty());

        boolean updated = matchHistoryService.updateMatchHistory(summonerName);
        assertFalse(updated);
    }
}