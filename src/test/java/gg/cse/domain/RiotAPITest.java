package gg.cse.domain;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = RiotAPI.class)
public class RiotAPITest {
    @Autowired
    RiotAPI riotAPI;

    @Test
    public void get_summoner_with_name_test() {
        String summonerName = "Hide on bush";
        String puuid = "0e8jipVL71Ss6Q1L3xGXwNGxcsE4wZFZWBaQ0wY8RXxWVzK-hrGnp49dr72AWyf7awzCoTEXvJfJrg";

        SummonerDto summonerDto = riotAPI.getSummonerWithName(summonerName);

        assertEquals(summonerDto.getPuuid(), puuid);
    }

    @Test
    public void get_summoner_with_name_when_no_such_summoner() {
        String summonerName = "Not Existing Summoner 12345678";

        SummonerDto summonerDto = riotAPI.getSummonerWithName(summonerName);

        assertNull(summonerDto);
    }

    @Test
    public void get_match_history_test() {
        String puuid = "0e8jipVL71Ss6Q1L3xGXwNGxcsE4wZFZWBaQ0wY8RXxWVzK-hrGnp49dr72AWyf7awzCoTEXvJfJrg";

        List<String> matchIDs = riotAPI.getMatchHistory(puuid);

        assertEquals(matchIDs.size(), 20);
    }

    @Test
    public void get_match_with_id() {
        String matchId = "KR_5360207611";

        MatchDto matchDto = riotAPI.getMatchWithId(matchId);

        // test if the classes are generated well
        assertEquals(matchDto
                        .getInfo()
                        .getParticipants()
                        .get(0)
                        .getPerks()
                        .getStyles()
                        .get(0)
                        .getSelections()
                        .get(0)
                        .getPerk(),
                8124);
        assertEquals(matchDto
                        .getMetadata()
                        .getParticipants()
                        .get(6),
                "0e8jipVL71Ss6Q1L3xGXwNGxcsE4wZFZWBaQ0wY8RXxWVzK-hrGnp49dr72AWyf7awzCoTEXvJfJrg");
    }
}
