package gg.cse.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private String summonerId;
    private long summonerLevel;

    @ManyToMany
    @JoinTable(name = "summoner_match")
    @OrderBy("game_creation DESC")
    @Builder.Default
    private Set<Match> matches = new LinkedHashSet<>();

    public void update(String accountId, int profileIconId, long revisionDate, String name, String summonerId, long summonerLevel) {
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.name = name;
        this.summonerId = summonerId;
        this.summonerLevel = summonerLevel;
    }

    // returns up to num recent Match elements
    public List<Match> getRecentMatches(int num) {
        return matches.stream().limit(num).collect(Collectors.toList());
    }

    public List<Match> getRecentMatches() {
        return getRecentMatches(20);
    }

    // add matches in sorted order except for those already exist
    public void addMatches(List<Match> matches) {
        matches.sort(Comparator.comparingLong(Match::getGameCreation));

        List<Match> newMatchList = new ArrayList<>(this.matches);
        long latestGameCreation = newMatchList.isEmpty() ?
                0 : newMatchList.get(newMatchList.size()-1).getGameCreation();
        matches.stream()
                .filter(match -> match.getGameCreation() > latestGameCreation)
                .forEach(newMatchList::add);
        this.matches = new LinkedHashSet<>(newMatchList);
    }
}
