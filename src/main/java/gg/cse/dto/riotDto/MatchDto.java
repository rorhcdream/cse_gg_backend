package gg.cse.dto.riotDto;

import gg.cse.domain.Match;
import gg.cse.domain.Participant;
import gg.cse.util.ModelMapperUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Slf4j
public class MatchDto {
    private Info info;
    private Metadata metadata;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    public static class Metadata {
        private List<String> participants;
        private String matchId;
        private String dataVersion;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    public static class Info {
        private long gameCreation;
        private long gameDuration;
        private long gameId;
        private String gameMode;
        private String gameName;
        private long gameStartTimeStamp;
        private String gameType;
        private String gameVersion;
        private int mapId;
        private List<Participant> participants;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    public static class Participant {
        private boolean win;
        private int wardsPlaced;
        private int wardsKilled;
        private int visionWardsBoughtInGame;
        private int visionScore;
        private int unrealKills;
        private int turretsLost;
        private int turretTakedowns;
        private int turretKills;
        private int trueDamageTaken;
        private int trueDamageDealtToChampions;
        private int trueDamageDealt;
        private int tripleKills;
        private int totalUnitsHealed;
        private int totalTimeSpentDead;
        private int totalTimeCCDealt;
        private int totalMinionsKilled;
        private int totalHealsOnTeammates;
        private int totalHeal;
        private int totalDamageTaken;
        private int totalDamageShieldedOnTeammates;
        private int totalDamageDealtToChampions;
        private int totalDamageDealt;
        private int timePlayed;
        private int timeCCingOthers;
        private String teamPosition;
        private int teamId;
        private boolean teamEarlySurrendered;
        private String summonerName;
        private int summonerLevel;
        private String summonerId;
        private int summoner2Id;
        private int summoner2Casts;
        private int summoner1Id;
        private int summoner1Casts;
        private int spell4Casts;
        private int spell3Casts;
        private int spell2Casts;
        private int spell1Casts;
        private int sightWardsBoughtInGame;
        private String role;
        private String riotIdTagline;
        private String riotIdName;
        private int quadraKills;
        private String puuid;
        private int profileIcon;
        private int physicalDamageTaken;
        private int physicalDamageDealtToChampions;
        private int physicalDamageDealt;
        private Perks perks;
        private int pentaKills;
        private int participantId;
        private int objectivesStolenAssists;
        private int objectivesStolen;
        private int nexusTakedowns;
        private int nexusLost;
        private int nexusKills;
        private int neutralMinionsKilled;
        private int magicDamageTaken;
        private int magicDamageDealtToChampions;
        private int magicDamageDealt;
        private int longestTimeSpentLiving;
        private int largestMultiKill;
        private int largestKillingSpree;
        private int largestCriticalStrike;
        private String lane;
        private int kills;
        private int killingSprees;
        private int itemsPurchased;
        private int item6;
        private int item5;
        private int item4;
        private int item3;
        private int item2;
        private int item1;
        private int item0;
        private int inhibitorsLost;
        private int inhibitorTakedowns;
        private int inhibitorKills;
        private String individualPosition;
        private int goldSpent;
        private int goldEarned;
        private boolean gameEndedInSurrender;
        private boolean gameEndedInEarlySurrender;
        private boolean firstTowerKill;
        private boolean firstTowerAssist;
        private boolean firstBloodKill;
        private boolean firstBloodAssist;
        private int dragonKills;
        private int doubleKills;
        private int detectorWardsPlaced;
        private int deaths;
        private int damageSelfMitigated;
        private int damageDealtToTurrets;
        private int damageDealtToObjectives;
        private int damageDealtToBuildings;
        private int consumablesPurchased;
        private int championTransform;
        private String championName;
        private int championId;
        private int champLevel;
        private int champExperience;
        private int bountyLevel;
        private int baronKills;
        private int assists;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        @ToString
        public static class Perks {
            private List<Styles> styles;
            private StatPerks statPerks;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        @ToString
        public static class Styles {
            private int style;
            private List<Selections> selections;
            private String description;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        @ToString
        public static class Selections {
            private int var3;
            private int var2;
            private int var1;
            private int perk;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        @ToString
        public static class StatPerks {
            private int offense;
            private int flex;
            private int defense;
        }
    }

    public static MatchDto of(Match entity) {
        ModelMapper modelMapper = ModelMapperUtil.get();

        MatchDto.Info info = Info.builder()
                .gameCreation(entity.getGameCreation())
                .gameDuration(entity.getGameDuration())
                .gameId(entity.getGameId())
                .gameMode(entity.getGameMode())
                .gameName(entity.getGameName())
                .gameStartTimeStamp(entity.getGameStartTimeStamp())
                .gameType(entity.getGameType())
                .gameVersion(entity.getGameVersion())
                .mapId(entity.getMapId())
                .participants(entity.getParticipants().stream()
                        .map(participant -> modelMapper.map(participant, Participant.class))
                        .collect(Collectors.toList())
                )
                .build();
        MatchDto.Metadata metadata = Metadata.builder()
                .matchId(entity.getMatchId())
                .dataVersion(entity.getDataVersion())
                .participants(entity.getParticipants().stream()
                        .map(gg.cse.domain.Participant::getPuuid)
                        .collect(Collectors.toList())
                )
                .build();
        return MatchDto.builder()
                .info(info)
                .metadata(metadata)
                .build();
    }

    public Match toEntity() {
        ModelMapper modelMapper = ModelMapperUtil.get();

        return Match.builder()
                .matchId(metadata.matchId)
                .dataVersion(metadata.dataVersion)
                .gameCreation(info.gameCreation)
                .gameId(info.gameId)
                .gameMode(info.gameMode)
                .gameName(info.gameName)
                .gameStartTimeStamp(info.gameStartTimeStamp)
                .gameType(info.gameType)
                .gameVersion(info.gameVersion)
                .mapId(info.mapId)
                .participants(info.participants.stream().map(
                        participant -> modelMapper.map(participant, gg.cse.domain.Participant.class)
                ).collect(Collectors.toSet()))
                .build();
    }
}
