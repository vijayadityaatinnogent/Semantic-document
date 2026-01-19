package com.example.opensearch.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final OpenSearchClient client;
    private final EmbeddingService embeddingService;
    private static final String INDEX = "students";

    public StudentService(OpenSearchClient client,
            EmbeddingService embeddingService) {
        this.client = client;
        this.embeddingService = embeddingService;
    }

    
    public String save(String id, String text, Map<String, Object> data) throws Exception {

        List<Float> vector = embeddingService.embed(text);
        data.put("embedding_text", text);
        data.put("embedding", vector);

        client.index(i -> i
                .index(INDEX)
                .id(id)
                .document(data));

        return "Student saved with BGE-M3 embedding";
    }

    // SEMANTIC SEARCH
    public List<Map<String, Object>> search(String query) throws Exception {

        List<Float> vector = embeddingService.embed(query);

        float[] vectorArray = new float[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            vectorArray[i] = vector.get(i).floatValue();
        }

        return client.search(s -> s
                .index(INDEX)
                .size(5)
                .query(q -> q.knn(k -> k
                        .field("embedding")
                        .vector(vectorArray)
                        .k(5))),
                Map.class).hits().hits().stream()
                .map(hit -> {
                    Map<String, Object> source = hit.source();
                    if (source != null) {
                        source.remove("embedding");
                    }
                    return source;
                })
                .toList();
    }

    public String deleteAll() throws Exception {

        client.deleteByQuery(d -> d
                .index("students")
                .query(q -> q.matchAll(m -> m)));

        return "All students deleted";
    }

    public String deleteById(String id) throws Exception {

        client.delete(d -> d
                .index("students")
                .id(id));

        return "Student deleted with id = " + id;
    }

}
