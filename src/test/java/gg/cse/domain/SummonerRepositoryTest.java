package gg.cse.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SummonerRepositoryTest {
    @Autowired
    SummonerRepository summonerRepository;

    @Autowired
    MatchRepository matchRepository;

    static Match match1, match2;
    Summoner summoner;

    @BeforeAll
    public static void beforeAll() {
        match1 = Match.builder()
                .matchId("matchid1")
                .gameId(1L)
                .build();
        match2 = Match.builder()
                .matchId("matchid2")
                .gameId(2L)
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        summoner = Summoner.builder()
                .id("id")
                .summonerLevel(123)
                .puuid("puuid")
                .name("name")
                .profileIconId(12)
                .revisionDate(1234567890123L)
                .accountId("accountid")
                .build();
    }

    @AfterEach
    public void cleanUp() {
        summonerRepository.deleteAll();
        matchRepository.deleteAll();
    }

    @Transactional
    @Test
    public void save_load_a_summoner() {
        summonerRepository.save(summoner);

        assertEquals(summoner, summonerRepository.findAll().get(0));
    }

    @Transactional
    @Test
    public void save_load_with_matches() {
        List<Match> matches = summoner.getMatches();
        matches.add(match1);
        matches.add(match2);
        for (Match m : matches) {
            matchRepository.save(m);
        }
        summonerRepository.save(summoner);

        Summoner loaded = summonerRepository.findAll().get(0);
        assertEquals(summoner, loaded);
    }

    @Transactional
    @Test
    public void add_matches() {
        summonerRepository.save(summoner);
        matchRepository.save(match1);
        matchRepository.save(match2);

        List<Match> matches = summoner.getMatches();
        matches.add(match1);
        matches.add(match2);
        summonerRepository.save(summoner);

        Summoner loaded = summonerRepository.findAll().get(0);
        assertEquals(summoner, loaded);
        assertEquals(summoner.getMatches().size(), 2);
    }

    @Transactional
    @Test
    public void update() {
        String id = summoner.getPuuid();
        String accountid = summoner.getAccountId();
        summonerRepository.save(summoner);
        summoner.update("1", 2, 3, "4", "5", 6);
        summonerRepository.save(summoner);

        Summoner loaded = summonerRepository.getById(id);
        assertEquals(summoner, loaded);
        assertNotEquals(loaded.getAccountId(), accountid);
    }
}