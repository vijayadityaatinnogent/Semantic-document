package com.example.centralized.CentralizedEmbedding.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.centralized.CentralizedEmbedding.model.SearchIndex;

public interface SearchIndexRepository extends MongoRepository<SearchIndex, String> {
}

