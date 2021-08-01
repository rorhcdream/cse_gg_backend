package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchApiController {
    private final SearchService searchService;

    @GetMapping("/api/v1/search/{summonerName}")
    public List<MatchDto> search(@PathVariable String summonerName) {
        return searchService.search(summonerName);
    }
}