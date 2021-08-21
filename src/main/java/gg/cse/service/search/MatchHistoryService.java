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

        List<Match> matches = summonerOptional.get().getRecentMatches();

        // update match history when no match history
        if (matches.isEmpty()) {
            self.updateMatchHistory(summonerName);
            matches = summonerRepository.findByNameIgnoreCase(summonerName).get().getRecentMatches();
            if (matches.isEmpty())
                return Optional.empty();
        }

        return Optional.of(
                matches.stream().map(MatchDto::of).collect(Collectors.toList())
        );
    }

    // returns true when update success, false when no such summoner
    @Transactional
    public boolean updateMatchHistory(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByNameIgnoreCase(summonerName);
        if (summonerOptional.isEmpty())  // no such summoner
            return false;

        Summoner summoner = summonerOptional.get();

        List<String> matchIds = riotAPI.getMatchHistory(summoner.getPuuid());
        List<Match> newMatches = matchIds.stream()
                .map(riotAPI::getMatchWithId)
                .map(MatchDto::toEntity)
                .collect(Collectors.toList());

        matchRepository.saveAll(newMatches);
        summoner.addMatches(newMatches);
        summonerRepository.save(summoner);
        return true;
    }
}
