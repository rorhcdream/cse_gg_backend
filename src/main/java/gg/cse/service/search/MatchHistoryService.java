package gg.cse.service.search;

import gg.cse.domain.*;
import gg.cse.dto.riotDto.MatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchHistoryService {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private RiotAPI riotAPI;

    @Autowired
    MatchHistoryService self;

    public Optional<List<MatchDto>> getMatchHistory(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByNameIgnoreCase(summonerName);

        if (summonerOptional.isEmpty())  // no such summoner
            return Optional.empty();

        List<Match> matches = summonerOptional.get().getMatches();

        // update match history when no match history
        if (matches.isEmpty()) {
            self.updateMatchHistory(summonerName);
            matches = summonerRepository.findByNameIgnoreCase(summonerName).get().getMatches();
            if (matches.isEmpty())
                return Optional.empty();
        }

        return Optional.of(
                matches.subList(Math.max(0, matches.size() - 20), matches.size())
                        .stream().map(MatchDto::of).collect(Collectors.toList())
        );
    }

    // returns true when update success, false when no such summoner
    @Transactional
    public boolean updateMatchHistory(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByNameIgnoreCase(summonerName);
        if (summonerOptional.isEmpty())  // no such summoner
            return false;

        Summoner summoner = summonerOptional.get();
        List<Match> summonerMatches = summoner.getMatches();
        long lastMatchTime = summonerMatches.isEmpty() ?
                 0 : summonerMatches.get(summonerMatches.size() - 1).getGameCreation();

        List<String> matchIds = riotAPI.getMatchHistory(summoner.getPuuid());
        matchIds.stream()
                .map(riotAPI::getMatchWithId)
                .filter(matchDto -> matchDto.getInfo().getGameCreation() > lastMatchTime)
                .map(MatchDto::toEntity)
                .forEach(match -> {
                    summonerMatches.add(match);
                    matchRepository.save(match);
                });
        summonerRepository.save(summoner);
        return true;
    }
}
