package com.example.centralized.CentralizedEmbedding.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "search_index")
public class SearchIndex {

    @Id
    private String id;

    private String sourceCollection; // jobs, freelancers
    private String sourceId;         // original document id
    private String text;             // searchable text
    private List<Double> embedding;

    public SearchIndex() {}

    public SearchIndex(String sourceCollection, String sourceId, String text, List<Double> embedding) {
        this.sourceCollection = sourceCollection;
        this.sourceId = sourceId;
        this.text = text;
        this.embedding = embedding;
    }

    // getters setters
}

