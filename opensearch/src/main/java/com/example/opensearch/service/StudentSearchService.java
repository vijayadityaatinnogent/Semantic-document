package com.example.opensearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentSearchService {

    private final OpenSearchClient client;
    private final EmbeddingService embeddingService; // python call

    public List<Map<String, Object>> knnSearch(String text) throws Exception {

        // 1. get embedding from python
        List<Float> vector = embeddingService.embed(text);

        float[] vectorArray = new float[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            vectorArray[i] = vector.get(i).floatValue();
        }

        // 2. knn search
        SearchResponse<Map> response = client.search(s -> s
                .index("students")
                .size(5)
                .query(q -> q
                        .knn(k -> k
                                .field("embedding")
                                .vector(vectorArray)
                                .k(5))),
                Map.class);

        // 3. extract results
        return response.hits().hits()
        .stream()
        .map(hit -> {
            Map<String, Object> source = hit.source();
            if (source != null) {
                source.remove("embedding");
            }
            return source;
        })
        .toList();

    }
}

