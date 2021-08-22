package gg.cse.dto.riotDto;

import gg.cse.domain.Summoner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SummonerDtoTest {
    @Test
    public void convert_between_summoner_and_summonerDto() {
        SummonerDto summonerDto = SummonerDto.builder()
                .accountId("account_id")
                .profileIconId(1)
                .revisionDate(123L)
                .name("name")
                .id("id")
                .puuid("puuid")
                .summonerLevel(321L)
                .build();

        Summoner summoner1 = summonerDto.toEntity();
        SummonerDto summonerDto1 = SummonerDto.of(summoner1);
        Summoner summoner2 = summonerDto1.toEntity();
        SummonerDto summonerDto2 = SummonerDto.of(summoner2);

        assertEquals(summonerDto, summonerDto1);
        assertEquals(summonerDto1, summonerDto2);
        assertEquals(summoner1, summoner2);
    }
}