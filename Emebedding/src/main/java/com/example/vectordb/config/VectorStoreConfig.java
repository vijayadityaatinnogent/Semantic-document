package com.example.vectordb.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.MongoDBAtlasVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class VectorStoreConfig {

    @Bean
    @Primary
    @Qualifier("cosineStore")
    public VectorStore cosineStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel, true);
    }

    @Bean
    @Qualifier("euclideanStore")
    public VectorStore euclideanStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
        return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel, true);
    }
}
