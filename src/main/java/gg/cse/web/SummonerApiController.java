package gg.cse.web;

import gg.cse.dto.SummonerInfoDto;
import gg.cse.service.search.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class SummonerApiController {
    private final SummonerService summonerService;

    @GetMapping("/api/v1/summoner/{summonerName}")
    public ResponseEntity<SummonerInfoDto> get_summoner(@PathVariable String summonerName) {
        Optional<SummonerInfoDto> result = summonerService.getSummoner(summonerName);
        return ResponseEntity.of(result);
    }

    @PostMapping("/api/v1/summoner/{summonerName}")
    public ResponseEntity<Void> update_summoner(@PathVariable String summonerName) {
        if (summonerService.updateSummoner(summonerName).isPresent())
            return ResponseEntity.ok(null);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
