package com.example.centralized.CentralizedEmbedding.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.centralized.CentralizedEmbedding.model.SearchIndex;
import com.example.centralized.CentralizedEmbedding.repository.SearchIndexRepository;

@Service
public class DataInsertService {

    private final EmbeddingService embeddingService;
    private final SearchIndexRepository repository;

    public DataInsertService(EmbeddingService embeddingService,
                             SearchIndexRepository repository) {
        this.embeddingService = embeddingService;
        this.repository = repository;
    }

    public SearchIndex addData(String sourceCollection,
                               String sourceId,
                               String text) {

        List<Double> embedding =
                embeddingService.generateEmbedding(text);

        SearchIndex index = new SearchIndex(
                sourceCollection,
                sourceId,
                text,
                embedding
        );

        return repository.save(index);
    }
}
