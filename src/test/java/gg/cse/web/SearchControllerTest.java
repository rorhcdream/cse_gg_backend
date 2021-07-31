package gg.cse.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = SearchController.class)
public class SearchControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void search_is_returned() throws Exception {
        String search = "search";

        mvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(content().string(search));
    }
}
