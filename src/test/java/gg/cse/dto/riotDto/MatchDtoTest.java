package gg.cse.dto.riotDto;

import gg.cse.domain.Match;
import gg.cse.dto.riotDto.MatchDto.Participant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MatchDtoTest {
    @Test
    public void convert_between_match_and_matchDto() {
        String matchId = "match_id";
        Long gameCreation = 1234567890123L;
        String summonerName1 = "summoner_name_1";
        String summonerName2 = "summoner_name_2";
        String puuid1 = "puuid1";
        String puuid2 = "puuid2";
        String dataVersion = "data_version";
        int style1 = 11, style2 = 12;
        int defense = 13;

        List<Participant.Styles> styles = new ArrayList<>();
        styles.add(Participant.Styles.builder()
                .style(style1)
                .build());
        styles.add(Participant.Styles.builder()
                .style(style2)
                .build());
        Participant.Perks perks = Participant.Perks.builder()
                .statPerks(Participant.StatPerks.builder()
                        .defense(defense)
                        .build())
                .styles(styles)
                .build();

        List<Participant> participants = new ArrayList<>();
        participants.add(Participant.builder()
                .puuid(puuid1)
                .summonerName(summonerName1)
                .perks(perks)
                .build());
        participants.add(Participant.builder()
                .puuid(puuid2)
                .summonerName(summonerName2)
                .build());

        MatchDto matchDto = MatchDto.builder()
                .info(MatchDto.Info.builder()
                        .gameCreation(gameCreation)
                        .participants(participants)
                        .build())
                .metadata(MatchDto.Metadata.builder()
                        .participants(List.of(puuid1, puuid2))
                        .dataVersion(dataVersion)
                        .matchId(matchId)
                        .build())
                .build();

        Match match = matchDto.toEntity();

        assertEquals(matchId, match.getMatchId());
        assertEquals(gameCreation, match.getGameCreation());
        assertEquals(Set.of(summonerName1, summonerName2),
                match.getParticipants().stream().map(gg.cse.domain.Participant::getSummonerName).collect(Collectors.toSet()));
        assertEquals(style1,
                match.getParticipants().stream()
                        .filter(participant -> participant.getPerks() != null).findFirst()
                        .get().getPerks().getStyles().get(0).getStyle());

        MatchDto converted = MatchDto.of(match);
        assertEquals(matchDto, converted);

        // convert twice to ensure no problem with calling the method more than once
        Match converted_match = converted.toEntity();
        assertEquals(converted_match, match);

        MatchDto converted2 = MatchDto.of(converted_match);
        assertEquals(converted2, converted);
    }
}