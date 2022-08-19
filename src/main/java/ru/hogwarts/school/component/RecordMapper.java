package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.record.StudentRecord;
import ru.hogwarts.school.record.StudentRecord2;

@Component
public class RecordMapper {

    public StudentRecord toRecord(Student student) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        studentRecord.setFaculty(toRecord(student.getFaculty()));
        return studentRecord;
    }

    public StudentRecord2 toRecord2(Student student) {
        StudentRecord2 studentRecord2 = new StudentRecord2();
        studentRecord2.setId(student.getId());
        studentRecord2.setName(student.getName());
        studentRecord2.setAge(student.getAge());
        return studentRecord2;
    }

    public FacultyRecord toRecord(Faculty faculty) {
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
        return facultyRecord;
    }

    public AvatarRecord toRecord(Avatar avatar) {
        AvatarRecord avatarRecord = new AvatarRecord();
        avatarRecord.setId(avatar.getId());
        avatarRecord.setFilePath(avatar.getFilePath());
        avatarRecord.setFileSize(avatar.getFileSize());
        avatarRecord.setMediaType(avatar.getMediaType());
        avatarRecord.setData(avatar.getData());
        return avatarRecord;
    }

    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        return student;
    }

    public Student toEntity2(StudentRecord2 studentRecord2) {
        Student student = new Student();
        student.setName(studentRecord2.getName());
        student.setAge(studentRecord2.getAge());
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyRecord.getName());
        faculty.setColor(facultyRecord.getColor());
        return faculty;
    }

    public Avatar toEntity(AvatarRecord avatarRecord) {
        Avatar avatar = new Avatar();
        avatar.setFilePath(avatarRecord.getFilePath());
        avatar.setFileSize(avatarRecord.getFileSize());
        avatar.setMediaType(avatarRecord.getMediaType());
        avatar.setData(avatarRecord.getData());
        return avatar;
    }
}
