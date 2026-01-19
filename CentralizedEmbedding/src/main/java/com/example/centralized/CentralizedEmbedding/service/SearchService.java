package com.example.centralized.CentralizedEmbedding.service;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final MongoTemplate mongoTemplate;
    private final EmbeddingService embeddingService;

    public SearchService(MongoTemplate mongoTemplate,
                         EmbeddingService embeddingService) {
        this.mongoTemplate = mongoTemplate;
        this.embeddingService = embeddingService;
    }

    public List<Document> search(String query) {

        List<Double> queryEmbedding =
                embeddingService.generateEmbedding(query);

        Document vectorSearch = new Document("$vectorSearch",
                new Document("index", "globalVectorIndex")
                        .append("path", "embedding")
                        .append("queryVector", queryEmbedding)
                        .append("numCandidates", 100)
                        .append("limit", 5));

        return mongoTemplate
                .getCollection("search_index")
                .aggregate(List.of(vectorSearch))
                .into(new java.util.ArrayList<>());
    }
}

