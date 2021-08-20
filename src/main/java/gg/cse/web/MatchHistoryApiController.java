package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.service.search.MatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MatchHistoryApiController {
    private final MatchHistoryService matchHistoryService;

    @GetMapping("/api/v1/match_history/{summonerName}")
    public ResponseEntity<List<MatchDto>> get_match_history(@PathVariable String summonerName) {
        Optional<List<MatchDto>> result = matchHistoryService.getMatchHistory(summonerName);

        return result.map(matchDtos -> new ResponseEntity<>(matchDtos, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/v1/match_history/{summonerName}")
    public ResponseEntity<Void> update_match_history(@PathVariable String summonerName) {
        if (matchHistoryService.updateMatchHistory(summonerName))
            return ResponseEntity.ok(null);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}