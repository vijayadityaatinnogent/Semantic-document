package com.example.vectordb.controller;

import com.example.vectordb.service.EmbeddingService;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class EmbeddingController {

    private final VectorStore cosineStore;
    private final VectorStore euclideanStore;
    private final EmbeddingService embeddingService;

    public EmbeddingController(@Qualifier("cosineStore") VectorStore cosineStore,
                              @Qualifier("euclideanStore") VectorStore euclideanStore,
                              EmbeddingService embeddingService) {
        this.cosineStore = cosineStore;
        this.euclideanStore = euclideanStore;
        this.embeddingService = embeddingService;
    }

    // Data store karne ke liye
    @PostMapping(value= "/add", consumes = "text/plain")
    public String addData(@RequestBody String msg) {
        Document doc = new Document(msg, Map.of("type", "video", "length", "20.07"));
        cosineStore.add(List.of(doc));
        euclideanStore.add(List.of(doc));
        return "Both stores mein save ho gaya!";
    }

    // Cosine similarity search
    @GetMapping("/search/cosine")
    public List<Document> searchCosine(@RequestParam String q) {
        return embeddingService.searchCosine(q);
    }

    // Euclidean similarity search
    @GetMapping("/search/euclidean")
    public List<Document> searchEuclidean(@RequestParam String q) {
        return embeddingService.searchEuclidean(q);
    }

    // Both methods se search
    @GetMapping("/search/both")
    public Map<String, List<Document>> searchBoth(@RequestParam String q) {
        return embeddingService.searchBoth(q);
    }

    @PostMapping("/add-json")
    public String addDataJson(@RequestBody Map<String, Object> payload) {
        String content = (String) payload.get("content");
        Map<String, Object> metadata = new java.util.HashMap<>(payload);
        metadata.remove("content");
        Document doc = new Document(content, metadata);
        cosineStore.add(List.of(doc));
        euclideanStore.add(List.of(doc));
        return "JSON body se data both stores mein save ho gaya!";
    }

    @PostMapping("/add-bulk")
    public String addBulkData(@RequestBody Map<String, Object> payload) {
        List<String> sentences = (List<String>) payload.get("content");
        Map<String, Object> metadata = new java.util.HashMap<>(payload);
        metadata.remove("content");
        List<Document> documents = sentences.stream()
                .map(sentence -> new Document(sentence, metadata))
                .toList();
        cosineStore.add(documents);
        euclideanStore.add(documents);
        return sentences.size() + " sentences stored in both stores!";
    }
    
    @PostMapping("/add-bulk-with-metadata")
    public String addBulkWithMetadata(@RequestBody Map<String, Object> payload) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
        List<Document> documents = items.stream()
            .map(item -> {
                String content = (String) item.get("content");
                Map<String, Object> metadata = new java.util.HashMap<>(item);
                metadata.remove("content");
                return new Document(content, metadata);
            })
            .toList();
        cosineStore.add(documents);
        euclideanStore.add(documents);
        return items.size() + " sentences with metadata stored in both stores!";
    }

    // Smart search with method selection
    @PostMapping("/smart-search")
    public Map<String, Object> smartSearch(@RequestBody Map<String, String> request) {
        String query = request.get("text");
        String method = request.getOrDefault("method", "both"); // cosine, euclidean, both
        
        if (query == null || query.trim().isEmpty()) {
            return Map.of("error", "Please provide 'text' field in request body");
        }
        
        return switch (method.toLowerCase()) {
            case "cosine" -> Map.of("method", "cosine", "results", 
                embeddingService.searchCosine(query).stream().map(Document::getContent).toList());
            case "euclidean" -> Map.of("method", "euclidean", "results", 
                embeddingService.searchEuclidean(query).stream().map(Document::getContent).toList());
            default -> {
                Map<String, List<Document>> bothResults = embeddingService.searchBoth(query);
                yield Map.of(
                    "cosine", bothResults.get("cosine").stream().map(Document::getContent).toList(),
                    "euclidean", bothResults.get("euclidean").stream().map(Document::getContent).toList()
                );
            }
        };
    }
}
