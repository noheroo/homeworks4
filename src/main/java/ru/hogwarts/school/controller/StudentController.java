package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.record.StudentRecord2;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentRecord> addStudent(@RequestBody StudentRecord studentRecord) {
        return ResponseEntity.ok(studentService.addStudent(studentRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRecord> findStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @PutMapping
    public ResponseEntity<StudentRecord> editStudent(@RequestBody StudentRecord studentRecord) {
        return ResponseEntity.ok(studentService.editStudent(studentRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentRecord> deleteStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<StudentRecord>> findStudentsByAgeBetween(@RequestParam int minAge,
                                                                        @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<FacultyRecord> getStudentFaculty(@PathVariable long id) {
        return ResponseEntity.ok(studentService.getStudentFaculty(id));
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuantityStudents() {
        return ResponseEntity.ok(studentService.getQuantityStudents());
    }
    @GetMapping("/averageAge")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }
    @GetMapping("/lastFiveStudents")
    public ResponseEntity<List<StudentRecord>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @GetMapping("/sorted/{firstLetter}")
    public ResponseEntity<List<StudentRecord2>> getSortedStudents(@PathVariable String firstLetter) {
        return ResponseEntity.ok(studentService.getSortedStudent(firstLetter));
    }

    @GetMapping("/getAverageAgeViaStream")
    public ResponseEntity<Double> getAverageAgeViaStream() {
        return ResponseEntity.ok(studentService.getAverageAgeViaStream());
    }

    @GetMapping ("/doSmt/{mode}")
    public void doSmt(@PathVariable boolean mode) {
        studentService.doSmt(mode);
    }
}
