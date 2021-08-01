package gg.cse.web;

import gg.cse.dto.riotDto.MatchDto;
import gg.cse.service.search.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SearchApiController.class)
public class SearchApiControllerTest {
    @MockBean
    SearchService searchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void search_test() throws Exception {
        List<MatchDto> matchDtos = new LinkedList<>();
        for(int i = 0; i < 20; i++) {
            matchDtos.add(new MatchDto());
        }
        when(searchService.search(any(String.class))).thenReturn(matchDtos);

        String summonerName = "Hide on bush";

        mockMvc.perform(get("/api/v1/search/" + summonerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[19].info").isEmpty());
    }
}
