package gg.cse.service.search;

import gg.cse.domain.Match;
import gg.cse.domain.MatchRepository;
import gg.cse.domain.Summoner;
import gg.cse.domain.SummonerRepository;
import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MatchHistoryService {
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;

    public List<MatchDto> matchHistory(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByName(summonerName);
        if (summonerOptional.isEmpty())  // no such summoner
            return null;

        List<Match> matches = summonerOptional.get().getMatches();

        return matches.stream().map(MatchDto::of).collect(Collectors.toList());
    }
}
