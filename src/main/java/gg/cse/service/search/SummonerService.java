package gg.cse.service.search;

import gg.cse.domain.RiotAPI;
import gg.cse.domain.Summoner;
import gg.cse.domain.SummonerRepository;
import gg.cse.dto.SummonerInfoDto;
import gg.cse.dto.riotDto.LeagueEntryDto;
import gg.cse.dto.riotDto.SummonerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SummonerService {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private RiotAPI riotAPI;

    @Transactional
    public Optional<SummonerInfoDto> getSummoner(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByNameIgnoreCase(summonerName);

        // update summoner info when no summoner in repository
        if (summonerOptional.isEmpty()) {
            summonerOptional = updateSummoner(summonerName);
            if(summonerOptional.isEmpty())
                return Optional.empty();
        }
        return Optional.of(SummonerInfoDto.of(summonerOptional.get()));
    }

    // returns Optional.empty() when no such summoner
    @Transactional
    public Optional<Summoner> updateSummoner(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByNameIgnoreCase(summonerName);
        SummonerDto summonerDto = riotAPI.getSummonerWithName(summonerName);
        if(summonerDto == null)
            return Optional.empty();

        Summoner updatedSummoner;
        if (summonerOptional.isPresent()) {
            updatedSummoner = summonerOptional.get();
            summonerDto.updateEntity(updatedSummoner);
        }
        else {
            updatedSummoner = summonerDto.toEntity();
        }

        Set<LeagueEntryDto> leagueEntryDtos = riotAPI.getLeagueEntryOfSummoner(updatedSummoner.getSummonerId());
        updatedSummoner.updateLeagueEntries(leagueEntryDtos.stream()
                .map(LeagueEntryDto::toEntity)
                .collect(Collectors.toSet()));

        summonerRepository.save(updatedSummoner);
        return Optional.of(updatedSummoner);
    }
}
