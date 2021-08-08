package gg.cse.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
public class Summoner {
    @Id
    private String puuid;
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private long summonerLevel;

    @ManyToMany
    @JoinTable(name = "summoner_match")
    @Builder.Default
    private List<Match> matches = new ArrayList<>();

    public void update(String accountId, int profileIconId, long revisionDate, String name, String id, long summonerLevel) {
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.name = name;
        this.id = id;
        this.summonerLevel = summonerLevel;
    }
}
