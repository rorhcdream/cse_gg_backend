package gg.cse.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MatchRepositoryTest {
    @Autowired
    MatchRepository matchRepository;

    static List<Participant.Styles> styles;
    static List<Participant.Selections> selections;
    static Set<Participant> participants;

    @BeforeAll
    static void beforeAll() {
        // build styles for perks
        styles = new ArrayList<>();
        selections = new ArrayList<>();
        selections.add(Participant.Selections.builder().build());
        selections.add(Participant.Selections.builder().build());

        styles.add(Participant.Styles.builder()
                .style(1)
                .selections(selections)
                .build()
        );
        styles.add(Participant.Styles.builder()
                .style(2)
                .selections(selections)
                .build()
        );

        // build participants
        participants = new HashSet<>();
        participants.add(Participant.builder()
                .summonerName("hide on bush")
                .summonerLevel(485)
                .perks(Participant.Perks.builder()
                        .statPerks(Participant.StatPerks.builder().build())
                        .styles(styles)
                        .build()
                )
                .build()
        );
        participants.add(Participant.builder()
                .summonerName("Summoner a")
                .summonerLevel(123)
                .perks(Participant.Perks.builder()
                        .statPerks(Participant.StatPerks.builder().build())
                        .styles(styles)
                        .build()
                )
                .build()
        );
    }

    @AfterEach
    public void cleanUp() { matchRepository.deleteAll(); }

    @Test
    public void match_equals() {
        Match match = Match.builder()
                .matchId("KR_5371736468")
                .gameCreation(1628150406000L)
                .participants(participants)
                .build();
        Match match1 = Match.builder()
                .matchId("KR_5371736468")
                .gameCreation(1628150406000L)
                .participants(participants)
                .build();
        assertEquals(match, match1);
    }

    @Transactional
    @Test
    public void save_load_a_match() {
        // build a match
        Match match = Match.builder()
                .matchId("KR_5371736468")
                .gameCreation(1628150406000L)
                .participants(participants)
                .build();

        matchRepository.save(match);
        matchRepository.save(match);

        List<Match> matches = matchRepository.findAll();
        Match match1 = matches.get(0);

        assertEquals(match, match1);
    }

    @Transactional
    @Test
    public void created_time() {
        LocalDateTime now = LocalDateTime.now();
        Match match = Match.builder()
                .matchId("KR_5371736468")
                .gameCreation(1628150406000L)
                .participants(participants)
                .build();

        matchRepository.save(match);

        List<Match> matches = matchRepository.findAll();
        Match resultMatch = matches.get(0);

        log.info("now: " + now);
        log.info("created: " + resultMatch.getCreatedDate());
        assertFalse(resultMatch.getCreatedDate().isBefore(now));
    }
}