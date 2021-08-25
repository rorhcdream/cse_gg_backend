package gg.cse.dto.riotDto;

import gg.cse.domain.LeagueEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeagueEntryDtoTest {
    @Test
    public void convert_between_dto_and_entity() {
        LeagueEntryDto dto = LeagueEntryDto.builder()
                .summonerName("summoner_name")
                .leagueId("league_id")
                .leaguePoints(12)
                .build();

        LeagueEntry converted = dto.toEntity();
        LeagueEntryDto converted1 = LeagueEntryDto.of(converted);
        LeagueEntry converted2 = converted1.toEntity();
        LeagueEntryDto converted3 = LeagueEntryDto.of(converted2);

        assertEquals(dto, converted1);
        assertEquals(converted, converted2);
        assertEquals(converted1, converted3);
    }
}