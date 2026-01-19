package com.example.opensearch.service;

import java.util.List;
import java.util.Map;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HybridSearchService {

    private final OpenSearchClient client;
    private final EmbeddingService embeddingService;

    public List<Map<String, Object>> search(Map<String, Object> request) throws Exception {

        String query = (String) request.get("query");
        List<String> textFields = (List<String>) request.get("textFields");
        String vectorField = (String) request.get("vectorField");
        int k = (int) request.getOrDefault("k", 5);
        int size = (int) request.getOrDefault("size", 5);
        Map<String, Object> filters = (Map<String, Object>) request.get("filters");

        // 1️⃣ Generate embedding
        List<Float> vector = embeddingService.embed(query);
        float[] vectorArray = toFloatArray(vector);

        // 2️⃣ Hybrid Search
        SearchResponse<Map> response = client.search(s -> s
                .index("students")
                .size(size)
                .query(q -> q.bool(b -> {

                    // ✅ STRICT FILTERS
                    if (filters != null) {
                        filters.forEach((key, value) -> {
                            FieldValue fieldValue;
                            if (value instanceof String) {
                                fieldValue = FieldValue.of((String) value);
                            } else if (value instanceof Integer) {
                                fieldValue = FieldValue.of((Integer) value);
                            } else if (value instanceof Long) {
                                fieldValue = FieldValue.of((Long) value);
                            } else {
                                fieldValue = FieldValue.of(value.toString());
                            }
                            b.filter(f -> f.term(t -> t.field(key).value(fieldValue)));
                        });
                    }

                    // BM25 (text fields)
                    for (String field : textFields) {
                        b.should(sh -> sh.match(m -> m
                                .field(field)
                                .query(FieldValue.of(query))));
                    }

                    // kNN
                    b.must(sh -> sh.knn(kq -> kq
                            .field(vectorField)
                            .vector(vectorArray)
                            .k(k)));

                    return b;
                })),
                Map.class);

        // 3️⃣ Clean response (remove embedding)
        return response.hits().hits().stream()
                .map(hit -> {
                    Map<String, Object> src = hit.source();
                    src.remove("embedding");
                    return src;
                })
                .toList();
    }

    private float[] toFloatArray(List<Float> list) {
        float[] arr = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
