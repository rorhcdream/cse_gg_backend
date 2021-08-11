package gg.cse.dto.riotDto;

import gg.cse.domain.Summoner;
import gg.cse.util.ModelMapperUtil;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SummonerDto {
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;

    public Summoner toEntity() {
        return Summoner.builder()
                .accountId(accountId)
                .profileIconId(profileIconId)
                .revisionDate(revisionDate)
                .name(name)
                .summonerId(id)
                .puuid(puuid)
                .summonerLevel(summonerLevel)
                .build();
    }

    public static SummonerDto of(Summoner entity) {
        return SummonerDto.builder()
                .accountId(entity.getAccountId())
                .profileIconId(entity.getProfileIconId())
                .revisionDate(entity.getRevisionDate())
                .name(entity.getName())
                .id(entity.getSummonerId())
                .puuid(entity.getPuuid())
                .summonerLevel(entity.getSummonerLevel())
                .build();
    }
}
