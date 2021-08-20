package gg.cse.service.search;

import gg.cse.domain.MatchRepository;
import gg.cse.domain.RiotAPI;
import gg.cse.domain.Summoner;
import gg.cse.domain.SummonerRepository;
import gg.cse.dto.riotDto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.of(summoner));

        Optional<SummonerDto> summonerDtoOptional = summonerService.getSummoner(summonerName);
        assertTrue(summonerDtoOptional.isPresent());
        assertEquals(summonerName, summonerDtoOptional.get().getName());
    }

    @Test
    public void get_summoner_who_is_not_in_database_yet() {
        String summonerName = "Hide on bush";
        Summoner summoner = Summoner.builder()
                .name(summonerName)
                .build();
        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.empty());
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(SummonerDto.of(summoner));
        when(summonerRepository.save(summoner)).thenReturn(null);

        Optional<SummonerDto> summonerDtoOptional = summonerService.getSummoner(summonerName);
        verify(summonerRepository).save(summoner);
        assertTrue(summonerDtoOptional.isPresent());
        assertEquals(summonerName, summonerDtoOptional.get().getName());
    }

    @Test
    public void get_summoner_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";
        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.empty());

        Optional<SummonerDto> summonerDtoOptional = summonerService.getSummoner(summonerName);
        assertTrue(summonerDtoOptional.isEmpty());
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
                .build();
        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.of(summoner));
        when(summonerRepository.save(any(Summoner.class))).thenReturn(null);
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(updateDto);

        Optional<Summoner> summonerOptional = summonerService.updateSummoner(summonerName);
        assertTrue(summonerOptional.isPresent());
        assertEquals(12, summonerService.getSummoner(summonerName).get().getSummonerLevel());
    }

    @Test
    public void update_summoner_when_no_such_summoner() {
        String summonerName = "not_existing_summoner_name";
        when(summonerRepository.findByName(summonerName)).thenReturn(Optional.empty());
        when(riotAPI.getSummonerWithName(summonerName)).thenReturn(null);

        Optional<Summoner> summonerOptional = summonerService.updateSummoner(summonerName);
        assertTrue(summonerOptional.isEmpty());
    }


}