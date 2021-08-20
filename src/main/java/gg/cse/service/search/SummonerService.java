package gg.cse.service.search;

import gg.cse.domain.RiotAPI;
import gg.cse.domain.Summoner;
import gg.cse.domain.SummonerRepository;
import gg.cse.dto.riotDto.SummonerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SummonerService {
    @Autowired
    private SummonerRepository summonerRepository;

    @Autowired
    private RiotAPI riotAPI;

    @Transactional
    public Optional<SummonerDto> getSummoner(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByName(summonerName);

        // update summoner info when no summoner in repository
        if (summonerOptional.isEmpty()) {
            summonerOptional = updateSummoner(summonerName);
            if(summonerOptional.isEmpty())
                return Optional.empty();
        }
        return Optional.of(SummonerDto.of(summonerOptional.get()));
    }

    // returns Optional.empty() when no such summoner
    @Transactional
    public Optional<Summoner> updateSummoner(String summonerName) {
        Optional<Summoner> summonerOptional = summonerRepository.findByName(summonerName);
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

        summonerRepository.save(updatedSummoner);
        return Optional.of(updatedSummoner);
    }
}
