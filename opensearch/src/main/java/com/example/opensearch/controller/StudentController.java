package com.example.opensearch.controller;

import com.example.opensearch.service.StudentSearchService;
import com.example.opensearch.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentSearchService studentSearchService;

    public StudentController(
            StudentService studentService,
            StudentSearchService studentSearchService) {
        this.studentService = studentService;
        this.studentSearchService = studentSearchService;
    }

    @PostMapping("/save")
    public String save(@RequestBody Map<String, Object> body) throws Exception {

        String id = body.get("id").toString();
        String embeddingText = body.get("embeddingText").toString();

        body.remove("id");
        body.remove("embeddingText");

        return studentService.save(id, embeddingText, body);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(
            @RequestParam String query) throws Exception {
        return studentService.search(query);
    }

    @GetMapping("/knn")
    public List<Map<String, Object>> knn(
            @RequestParam String q) throws Exception {
        return studentSearchService.knnSearch(q);
    }

    @DeleteMapping("/delete-all")
    public String deleteAll() throws Exception {
        return studentService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable String id) throws Exception {
        return studentService.deleteById(id);
    }

}
