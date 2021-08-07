package gg.cse.service.search;

import gg.cse.domain.RiotAPI;
import gg.cse.dto.riotDto.MatchDto;
import gg.cse.dto.riotDto.SummonerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchHistoryService {
    private final RiotAPI riotAPI;

    public List<MatchDto> matchHistory(String summonerName) {
        SummonerDto summonerDto = riotAPI.getSummonerWithName(summonerName);
        if (summonerDto == null) return null; // no such summoner

        List<String> matchIds = riotAPI.getMatchHistory(summonerDto.getPuuid());
        List<MatchDto> matchDtos = new LinkedList<>();

        for (String id : matchIds) {
            matchDtos.add(riotAPI.getMatchWithId(id));
        }

        return matchDtos;
    }
}
