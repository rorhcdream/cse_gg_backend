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
    @OrderBy("game_creation ASC")
    @Builder.Default
    private Set<Match> matches = new LinkedHashSet<>();

    @ElementCollection
    @CollectionTable(name="summoner_leagueEntries", joinColumns = @JoinColumn(name = "puuid"))
    private Set<LeagueEntry> leagueEntries;

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
        return new ArrayList<>(matches).subList(Math.max(matches.size() - num, 0), matches.size());
    }

    public List<Match> getRecentMatches() {
        return getRecentMatches(20);
    }

    // add matches in sorted order, only add matches that are created later than the last already existing match
    public void addMatches(List<Match> matches) {
        matches.sort(Comparator.comparingLong(Match::getGameCreation));
        long lastGameCreation = this.matches.isEmpty() ?
                0 : new ArrayList<>(this.matches).get(this.matches.size() - 1).getGameCreation();
        matches.stream().filter(match -> match.getGameCreation() > lastGameCreation).forEach(this.matches::add);
    }
}
