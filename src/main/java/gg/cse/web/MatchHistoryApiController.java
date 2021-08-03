package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.service.search.MatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MatchHistoryApiController {
    private final MatchHistoryService matchHistoryService;

    @GetMapping("/api/v1/match_history/{summonerName}")
    public ResponseEntity<List<MatchDto>> match_history(@PathVariable String summonerName) {
        List<MatchDto> result = matchHistoryService.matchHistory(summonerName);
        if(result == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(result, HttpStatus.OK);
    }
}