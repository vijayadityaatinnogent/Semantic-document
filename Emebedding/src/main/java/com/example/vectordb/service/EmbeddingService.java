package com.example.vectordb.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    private final VectorStore cosineStore;
    private final VectorStore euclideanStore;

    public EmbeddingService(@Qualifier("cosineStore") VectorStore cosineStore,
                           @Qualifier("euclideanStore") VectorStore euclideanStore) {
        this.cosineStore = cosineStore;
        this.euclideanStore = euclideanStore;
    }

    public void storeLocal(String content) {
        Document doc = new Document(content, Map.of("source", "local_machine"));
        // Both stores mein same data store karo
        cosineStore.add(List.of(doc));
        euclideanStore.add(List.of(doc));
        System.out.println("Local embedding stored in both stores!");
    }

    public List<Document> searchCosine(String query) {
        return cosineStore.similaritySearch(query);
    }

    public List<Document> searchEuclidean(String query) {
        return euclideanStore.similaritySearch(query);
    }

    public Map<String, List<Document>> searchBoth(String query) {
        return Map.of(
            "cosine", cosineStore.similaritySearch(query),
            "euclidean", euclideanStore.similaritySearch(query)
        );
    }
}
