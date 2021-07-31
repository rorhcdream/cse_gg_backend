package gg.cse.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @GetMapping("/search")
    public String search() {
        return "search";
    }
}