package gg.cse.service.search;

import gg.cse.domain.RiotAPI;
import gg.cse.domain.Summoner;
import gg.cse.domain.SummonerRepository;
import gg.cse.dto.SummonerInfoDto;
import gg.cse.dto.riotDto.LeagueEntryDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SummonerServiceTest {
    @Mock
    SummonerRepository summonerRepository;

    @Mock
    RiotAPI riotAPI;

    @InjectMocks
    SummonerService summonerService;

    @Test
    public void get_summoner_test() {
        String summonerName = "Hide on bush";
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .build();
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.of(summoner));

        Optional<SummonerInfoDto> summonerInfoDtoOptional = summonerService.getSummoner(summonerName);
        assertTrue(summonerInfoDtoOptional.isPresent());
        assertEquals(summonerName, summonerInfoDtoOptional.get().getSummoner().getName());
    }

    @Test
    public void get_summoner_who_is_not_in_database_yet() {
        String summonerName = "Hide on bush";
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .build();
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.empty()).thenReturn(Optional.of(summoner));
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(SummonerDto.of(summoner));
        when(summonerRepository.save(summoner)).thenReturn(null);

        Optional<SummonerInfoDto> summonerInfoDtoOptional = summonerService.getSummoner(summonerName);
        verify(summonerRepository).save(summoner);
        assertTrue(summonerInfoDtoOptional.isPresent());
        assertEquals(summonerName, summonerInfoDtoOptional.get().getSummoner().getName());
    }

    @Test
    public void get_summoner_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.empty());

        Optional<SummonerInfoDto> summonerInfoDtoOptional = summonerService.getSummoner(summonerName);
        assertTrue(summonerInfoDtoOptional.isEmpty());
    }

    @Test
    public void update_summoner_test() {
        String summonerName = "Hide on bush";
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .summonerLevel(11)
                .build();
        SummonerDto updateDto = SummonerDto.builder()
                .name(summonerName)
                .summonerLevel(12)
                .id("summoner_id")
                .build();
        LeagueEntryDto leagueEntryDto = LeagueEntryDto.builder()
                .summonerName(summonerName)
                .leagueId("league_id")
                .leaguePoints(12)
                .build();
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.of(summoner));
        when(summonerRepository.save(any(Summoner.class))).thenReturn(null);
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(updateDto);
        when(riotAPI.getLeagueEntryOfSummoner(any(String.class))).thenReturn(Set.of(leagueEntryDto));

        Optional<Summoner> summonerOptional = summonerService.updateSummoner(summonerName);
        SummonerInfoDto summonerInfoDto = summonerService.getSummoner(summonerName).get();
        assertTrue(summonerOptional.isPresent());
        assertEquals(12, summonerInfoDto.getSummoner().getSummonerLevel());
        assertTrue(summonerInfoDto.getLeagueEntries().contains(leagueEntryDto));
    }

    @Test
    public void update_summoner_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";
        when(summonerRepository.findByNameIgnoreCase(summonerName)).thenReturn(Optional.empty());
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(null);

        Optional<Summoner> summonerOptional = summonerService.updateSummoner(summonerName);
        assertTrue(summonerOptional.isEmpty());
    }


}