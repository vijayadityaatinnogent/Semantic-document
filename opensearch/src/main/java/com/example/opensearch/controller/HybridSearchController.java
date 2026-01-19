package com.example.opensearch.controller;

import com.example.opensearch.service.HybridSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class HybridSearchController {

    private final HybridSearchService hybridSearchService;

    public HybridSearchController(HybridSearchService hybridSearchService) {
        this.hybridSearchService = hybridSearchService;
    }

    @PostMapping("/hybrid-search")
    public List<Map<String, Object>> hybridSearch(
            @RequestBody Map<String, Object> request
    ) throws Exception {
        return hybridSearchService.search(request);
    }
}
