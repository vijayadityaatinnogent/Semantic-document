package com.example.exactSemantic.service;

import com.example.exactSemantic.model.Student;
import com.example.exactSemantic.repository.StudentRepository;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public void saveStudent(Student student) {
        repository.addStudentToVectorStore(student);
    }

    public List<Student> searchStudents(String query, String filterField, double threshold) {
        List<Document> results = repository.findSimilarStudentsWithFilter(query, filterField, threshold);

        return results.stream().map(this::mapDocumentToStudent).collect(Collectors.toList());
    }

    public List<Student> getPureSemanticSearch(String query) {
        List<Document> results = repository.findPureSemantic(query);
        return results.stream().map(this::mapDocumentToStudent).collect(Collectors.toList());
    }

    private Student mapDocumentToStudent(Document doc) {
        var meta = doc.getMetadata();
        return new Student(
                doc.getId(),
                (String) meta.get("name"),
                ((Number) meta.get("age")).intValue(),
                ((Number) meta.get("marks")).doubleValue(),
                ((Number) meta.get("height")).doubleValue(),
                ((Number) meta.get("weight")).doubleValue(),
                doc.getContent()
        );
    }
}