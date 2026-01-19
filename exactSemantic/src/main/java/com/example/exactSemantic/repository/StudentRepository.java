package com.example.exactSemantic.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class StudentRepository {

    private final VectorStore vectorStore;

    public StudentRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    // Har student ko save karte waqt metadata attach karna zaroori hai
    public void addStudentToVectorStore(com.example.exactSemantic.model.Student student) {
        Document doc = new Document(student.getDescription(), Map.of(
                "name", student.getName(),
                "age", student.getAge(),
                "marks", student.getMarks(),
                "height", student.getHeight(),
                "weight", student.getWeight()
        ));
        vectorStore.add(List.of(doc));
    }

    public List<Document> findSimilarStudentsWithFilter(String query, String field, double threshold) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        // Industry Best Practice: Pre-filtering before Vector Search
        SearchRequest request = SearchRequest.query(query)
                .withTopK(5)
                .withSimilarityThreshold(0.6)
                .withFilterExpression(b.eq(field, threshold).build());

        return vectorStore.similaritySearch(request);
    }

    public List<Document> findPureSemantic(String query) {

        SearchRequest request = SearchRequest.query(query)
                .withTopK(5)
                .withSimilarityThreshold(0.5);

        return vectorStore.similaritySearch(request);
    }
}