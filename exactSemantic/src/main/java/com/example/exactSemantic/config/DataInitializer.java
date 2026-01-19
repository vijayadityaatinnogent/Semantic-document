//package com.example.exactSemantic.config;
//
//import com.example.exactSemantic.model.Student;
//import com.example.exactSemantic.service.StudentService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final StudentService studentService;
//
//    public DataInitializer(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Sample students with different marks, age, height, weight
//        studentService.saveStudent(new Student("1", "Rahul", 20, 85.5, 175.0, 70.0,
//            "Excellent student with high marks in mathematics and science"));
//
//        studentService.saveStudent(new Student("2", "Priya", 19, 92.0, 160.0, 55.0,
//            "Outstanding performer in academics with top grades"));
//
//        studentService.saveStudent(new Student("3", "Amit", 21, 15.5, 180.0, 75.0,
//            "Struggling student needs improvement in studies"));
//
//        studentService.saveStudent(new Student("4", "Sneha", 18, 78.0, 165.0, 60.0,
//            "Good student with consistent performance"));
//
//        studentService.saveStudent(new Student("5", "Vikram", 22, 25.0, 170.0, 68.0,
//            "Average student with potential for growth"));
//    }
//}