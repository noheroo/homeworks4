package ru.hogwarts.homeworks4.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.EntranceAgesAreWrongException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.exception.StudentWithoutFacultyException;
import ru.hogwarts.school.handler.repository.FacultyRepository;
import ru.hogwarts.school.handler.repository.StudentRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
        this.facultyRepository = facultyRepository;
    }

    public StudentRecord addStudent(StudentRecord studentRecord) {
        Student newStudent = recordMapper.toEntity(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId())
                    .orElseThrow(FacultyNotFoundException::new);
            newStudent.setFaculty(faculty);
        }
        return recordMapper.toRecord(studentRepository.save(newStudent));
    }

    public StudentRecord findStudent(long id) {
        return studentRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(StudentNotFoundException::new);
    }

    public StudentRecord editStudent(StudentRecord studentRecord) {
        Student newStudent = studentRepository.findById(studentRecord.getId())
                .orElseThrow(StudentNotFoundException::new);
        newStudent.setName(studentRecord.getName());
        newStudent.setAge(studentRecord.getAge());
        return recordMapper.toRecord(studentRepository.save(newStudent));
    }

    public StudentRecord deleteStudent(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public List<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        if (minAge <= 0 || maxAge <= 0 || minAge > maxAge) {
            throw new EntranceAgesAreWrongException();
        }
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getStudentFaculty(long id) {
        if (findStudent(id).getFaculty() == null) {
            throw new StudentWithoutFacultyException();
        }
        return findStudent(id).getFaculty();
    }
}
