package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.EntranceAgesAreWrongException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.exception.StudentWithoutFacultyException;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
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

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentRecord addStudent(StudentRecord studentRecord) {
        logger.info("Was invoked method for add new student");
        Student newStudent = recordMapper.toEntity(studentRecord);
        if (studentRecord.getFaculty() != null) {
            Faculty faculty = facultyRepository.findById(studentRecord.getFaculty().getId())
                    .orElseThrow(() -> {
                                logger.error("Факультет не найден");
                                return new FacultyNotFoundException();
                            }
                    );
            newStudent.setFaculty(faculty);
        }
        return recordMapper.toRecord(studentRepository.save(newStudent));
    }

    public StudentRecord findStudent(long id) {
        logger.info("Was invoked method for find existing student");
        return studentRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(() -> {
                            logger.error("Студент не найден");
                            return new StudentNotFoundException();
                        }
                );

    }

    public StudentRecord editStudent(StudentRecord studentRecord) {
        logger.info("Was invoked method for edit existing student");
        Student newStudent = studentRepository.findById(studentRecord.getId())
                .orElseThrow(() -> {
                            logger.error("Студент не найден");
                            return new StudentNotFoundException();
                        }
                );
        newStudent.setName(studentRecord.getName());
        newStudent.setAge(studentRecord.getAge());
        logger.debug("Student ready for update");
        return recordMapper.toRecord(studentRepository.save(newStudent));
    }

    public StudentRecord deleteStudent(long id) {
        logger.info("Was invoked method for delete student");
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                            logger.error("Студент не найден");
                            return new StudentNotFoundException();
                        }
                );
        studentRepository.delete(student);
        return recordMapper.toRecord(student);
    }

    public List<StudentRecord> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find student by age between");
        if (minAge <= 0 || maxAge <= 0 || minAge > maxAge) {
            logger.error("Entrance ages are wrong");
            throw new EntranceAgesAreWrongException();
        }
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getStudentFaculty(long id) {
        logger.info("Was invoked method for get student's faculty");
        if (findStudent(id).getFaculty() == null) {
            logger.error("Student {} is without faculty", id);
            throw new StudentWithoutFacultyException();
        }
        return findStudent(id).getFaculty();
    }

    public Integer getQuantityStudents() {
        logger.info("Was invoked method for get quantity students in school");
        return studentRepository.getQuantityStudents();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAge();
    }

    public List<StudentRecord> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students in school database");
        return studentRepository.getLastFiveStudents().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }
}
