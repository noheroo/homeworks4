package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<FacultyRecord> addFaculty(@RequestBody FacultyRecord facultyRecord) {
        return ResponseEntity.ok(facultyService.addFaculty(facultyRecord));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyRecord> findFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @PutMapping
    public ResponseEntity<FacultyRecord> editFaculty(@RequestBody FacultyRecord facultyRecord) {
        return ResponseEntity.ok(facultyService.editFaculty(facultyRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FacultyRecord> deleteFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FacultyRecord>> findFacultiesByColorOrName(@RequestParam String nameOrColor) {
        return ResponseEntity.ok(facultyService.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentRecord>> getStudentsOfFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(id));
    }

    @GetMapping("/facultyWhichHasTheLongestName")
    public ResponseEntity<FacultyRecord> getFacultyWhichHasTheLongestName() {
        return ResponseEntity.ok(facultyService.getFacultyWhichHasTheLongestName());
    }
}
