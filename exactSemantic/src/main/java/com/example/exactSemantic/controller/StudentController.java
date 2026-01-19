package com.example.exactSemantic.controller;

import com.example.exactSemantic.model.Student;
import com.example.exactSemantic.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public String add(@RequestBody Student student) {
        service.saveStudent(student);
        return "Student added successfully!";
    }

    // Example query: /search?query=intelligent student&field=marks&threshold=18
    @GetMapping("/search")
    public List<Student> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "marks") String field,
            @RequestParam(defaultValue = "0") double threshold) {
        return service.searchStudents(query, field, threshold);
    }

    // URL: http://localhost:8080/api/students/pure-search?query=intelligent student
    @GetMapping("/pure-search")
    public List<Student> pureSearch(@RequestBody Map<String,String> query) {
        String queryStr = query.get("query");
        return service.getPureSemanticSearch(queryStr);
    }

    @PostMapping("/search-json")
    public List<Student> searchJson(@RequestBody Map<String, Object> body) {
        String query = (String) body.get("query");
        String field = body.getOrDefault("field", "marks").toString();
        Double threshold = Double.parseDouble(body.getOrDefault("threshold", "0").toString());
        return service.searchStudents(query, field, threshold);
    }
}