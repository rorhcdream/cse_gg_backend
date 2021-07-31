package gg.cse.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RiotAPITest {
    @Test
    public void get_summoner_with_name_test() throws Exception {
        RiotAPI riotAPI = new RiotAPI();

        String summonerName = "Proyan";
        String puuid = "9BBu7LLenBjXfFhI4dZvwKDm3gNGNfyioRFvZTxVJIGfWbo05_d8Hsm10n7xIqMMx5IAXNZW06ZlTA";

        assertEquals(riotAPI.getSummonerWithName(summonerName).getPuuid(), puuid);
    }
}
