package gg.cse.dto.riotDto;

import gg.cse.domain.Match;
import gg.cse.dto.riotDto.MatchDto.Participant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchDtoTest {
    @Test
    public void convert_to_match() {
        String matchId = "match_id";
        Long gameCreation = 1234567890123L;
        String summonerName1 = "summoner_name_1";
        String summonerName2 = "summoner_name_2";
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
                .summonerName(summonerName1)
                .perks(perks)
                .build());
        participants.add(Participant.builder()
                .summonerName(summonerName2)
                .build());

        MatchDto matchDto = MatchDto.builder()
                .info(MatchDto.Info.builder()
                        .gameCreation(gameCreation)
                        .participants(participants)
                        .build())
                .metadata(MatchDto.Metadata.builder()
                        .matchId(matchId)
                        .build())
                .build();

        Match match = matchDto.toEntity();

        assertEquals(matchId, match.getMatchId());
        assertEquals(gameCreation, match.getGameCreation());
        assertEquals(summonerName1, match.getParticipants().get(0).getSummonerName());
        assertEquals(summonerName2, match.getParticipants().get(1).getSummonerName());
        assertEquals(defense, match.getParticipants().get(0).getPerks().getStatPerks().getDefense());
        assertEquals(style1, match.getParticipants().get(0).getPerks().getStyles().get(0).getStyle());
        assertEquals(style2, match.getParticipants().get(0).getPerks().getStyles().get(1).getStyle());
    }
}