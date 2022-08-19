package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.EntranceAgesAreWrongException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.exception.StudentWithoutFacultyException;
import ru.hogwarts.school.record.StudentRecord2;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.*;

import static org.apache.commons.lang3.StringUtils.*;

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

    public List<StudentRecord2> getSortedStudent(String firstLetter) {
        logger.info("Was invoked method for get List with all students, which names started on letter {}", firstLetter);
        return studentRepository.findAll().stream()
                .filter(s -> startsWithIgnoreCase(s.getName(), firstLetter))
                .peek(s -> s.setName(capitalize(s.getName())))
                .sorted(Comparator.comparing(Student::getName))
                .map(recordMapper::toRecord2)
                .collect(Collectors.toList());
    }

    public Double getAverageAgeViaStream() {
        logger.info("Was invoked method for get average age of students via stream");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(StudentNotFoundException::new);
    }

    public void doSmt(boolean mode) {
        if (mode) {
            long start1 = System.currentTimeMillis();
            int sum1 = Stream.iterate(1, a -> a + 1)
                    .limit(1_000_000)
                    .reduce(0, (a, b) -> a + b);
            long time1 = System.currentTimeMillis() - start1;
            logger.info("Measuring time without .parallel() is {}", time1);
        } else {
            long start2 = System.currentTimeMillis();
            int sum2 = Stream.iterate(1, a -> a + 1)
                    .parallel()
                    .limit(1_000_000)
                    .reduce(0, (a, b) -> a + b);
            long time2 = System.currentTimeMillis() - start2;
            logger.info("Measuring time with .parallel() is {}", time2);
        }
    }

    public void getListOfNames1() {
        List<String> listOfNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        printNames(listOfNames, 0);
        printNames(listOfNames, 1);

        new Thread(() -> {
            printNames(listOfNames, 2);
            printNames(listOfNames, 3);
        }).start();

        new Thread(() -> {
            printNames(listOfNames, 4);
            printNames(listOfNames, 5);
        }).start();
    }

    public void getListOfNames2() {
        List<String> listOfNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        printNamesSynchronized(listOfNames, 0);
        printNamesSynchronized(listOfNames, 1);

        new Thread(() -> {
            printNamesSynchronized(listOfNames, 2);
            printNamesSynchronized(listOfNames, 3);
        }).start();

        new Thread(() -> {
            printNamesSynchronized(listOfNames, 4);
            printNamesSynchronized(listOfNames, 5);
        }).start();
    }

    private void printNames(List<String> listOfNames, int number) {
        System.out.println(listOfNames.get(number));
    }

    private synchronized void printNamesSynchronized(List<String> listOfNames, int number) {
        System.out.println(listOfNames.get(number));
    }

}
