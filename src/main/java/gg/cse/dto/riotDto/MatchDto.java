package gg.cse.dto.riotDto;

import gg.cse.domain.Match;
import gg.cse.domain.Participant;
import gg.cse.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDto {
    private Info info;
    private Metadata metadata;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Metadata {
        private List<String> participants;
        private String matchId;
        private String dataVersion;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
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
        public static class Perks {
            private List<Styles> styles;
            private StatPerks statPerks;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Styles {
            private int style;
            private List<Selections> selections;
            private String description;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
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
        public static class StatPerks {
            private int offense;
            private int flex;
            private int defense;
        }
    }

    public Match toEntity() {
        ModelMapper modelMapper = ModelMapperUtil.get();

        modelMapper.createTypeMap(MatchDto.class, Match.class).setProvider(
                request -> {
                    MatchDto matchDto = (MatchDto) request.getSource();
                    return new Match(
                            matchDto.metadata.matchId,
                            matchDto.metadata.dataVersion,
                            matchDto.info.gameCreation,
                            matchDto.info.gameDuration,
                            matchDto.info.gameId,
                            matchDto.info.gameMode,
                            matchDto.info.gameName,
                            matchDto.info.gameStartTimeStamp,
                            matchDto.info.gameType,
                            matchDto.info.gameVersion,
                            matchDto.info.mapId,
                            matchDto.info.participants.stream().map(
                                    participant -> modelMapper.map(participant, gg.cse.domain.Participant.class)
                            ).collect(Collectors.toList())
                    );
                });
        return modelMapper.map(this, Match.class);
    }
}
