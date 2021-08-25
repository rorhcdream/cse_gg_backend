package gg.cse.dto.riotDto;

import gg.cse.domain.LeagueEntry;
import gg.cse.util.ModelMapperUtil;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class LeagueEntryDto {
    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;

    public LeagueEntry toEntity() {
        ModelMapper modelMapper = ModelMapperUtil.get();
        return modelMapper.map(this, LeagueEntry.class);
    }

    public static LeagueEntryDto of(LeagueEntry entity) {
        ModelMapper modelMapper = ModelMapperUtil.get();
        return modelMapper.map(entity, LeagueEntryDto.class);
    }
}
