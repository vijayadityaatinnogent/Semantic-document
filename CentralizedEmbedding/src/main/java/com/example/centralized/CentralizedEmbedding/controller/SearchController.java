package com.example.centralized.CentralizedEmbedding.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import com.example.centralized.CentralizedEmbedding.model.SearchIndex;
import com.example.centralized.CentralizedEmbedding.service.DataInsertService;
import com.example.centralized.CentralizedEmbedding.service.SearchService;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final DataInsertService dataInsertService;
    private final SearchService searchService;

    public SearchController(DataInsertService dataInsertService,
                            SearchService searchService) {
        this.dataInsertService = dataInsertService;
        this.searchService = searchService;
    }

    // 🔹 ADD DATA (SIMPLE)
    @PostMapping("/add")
    public SearchIndex add(@RequestParam String sourceCollection,
                           @RequestParam String sourceId,
                           @RequestParam String text) {

        return dataInsertService.addData(
                sourceCollection,
                sourceId,
                text
        );
    }

    // 🔹 SEARCH
    @GetMapping("/search")
    public List<Document> search(@RequestParam String q) {
        return searchService.search(q);
    }
}
