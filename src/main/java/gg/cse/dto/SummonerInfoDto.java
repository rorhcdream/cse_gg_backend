package gg.cse.dto;

import gg.cse.domain.Summoner;
import gg.cse.dto.riotDto.LeagueEntryDto;
import gg.cse.dto.riotDto.SummonerDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SummonerInfoDto {
    private SummonerDto summoner;
    private Set<LeagueEntryDto> leagueEntries;
    private LocalDateTime lastUpdated;

    public static SummonerInfoDto of(Summoner entity) {
        return SummonerInfoDto.builder()
                .summoner(SummonerDto.of(entity))
                .leagueEntries(entity.getLeagueEntries().stream()
                        .map(LeagueEntryDto::of)
                        .collect(Collectors.toSet()))
                .lastUpdated(entity.getModifiedDate())
                .build();
    }
}
