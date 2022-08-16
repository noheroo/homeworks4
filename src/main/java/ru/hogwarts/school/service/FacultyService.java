package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.EntranceColorOrNameAreWrongException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final RecordMapper recordMapper;

    public FacultyService(FacultyRepository facultyRepository,
                          RecordMapper recordMapper) {
        this.facultyRepository = facultyRepository;
        this.recordMapper = recordMapper;
    }

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyRecord addFaculty(FacultyRecord facultyRecord) {
        logger.info("Was invoked method for add new faculty");
        return recordMapper.toRecord(facultyRepository.save(recordMapper.toEntity(facultyRecord)));
    }

    public FacultyRecord findFaculty(long id) {
        logger.info("Was invoked method for find existing faculty");
        return facultyRepository.findById(id)
                .map(recordMapper::toRecord)
                .orElseThrow(() -> {
                            logger.error("Факультет не найден");
                            return new FacultyNotFoundException();
                        }
                );
    }

    public FacultyRecord editFaculty(FacultyRecord facultyRecord) {
        logger.info("Was invoked method for edit existing faculty");
        Faculty newFaculty = recordMapper.toEntity(findFaculty(facultyRecord.getId()));
        newFaculty.setName(facultyRecord.getName());
        newFaculty.setColor(facultyRecord.getColor());
        logger.debug("Faculty ready for update");
        return recordMapper.toRecord(facultyRepository.save(newFaculty));
    }

    public FacultyRecord deleteFaculty(long id) {
        logger.info("Was invoked method for delete faculty");
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Факультет не найден");
                    return new FacultyNotFoundException();
                });
        facultyRepository.delete(faculty);
        return recordMapper.toRecord(faculty);
    }

    public List<FacultyRecord> findFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for find faculty by color or name, ignore case");
        if (name.isBlank() || color.isBlank()) {
            logger.error("Color or name are incorrect");
            throw new EntranceColorOrNameAreWrongException();
        }
        return facultyRepository.findFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color).stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

    public List<StudentRecord> getStudentsOfFaculty(long id) {
        logger.info("Was invoked method for get list of student for needed faculty");
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                            logger.error("Факультет не найден");
                            return new FacultyNotFoundException();
                        }
                )
                .getStudents().stream()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}
