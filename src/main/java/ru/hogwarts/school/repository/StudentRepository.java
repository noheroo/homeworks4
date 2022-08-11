package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    @Query(nativeQuery = true, value = "select count(*) from students")
    Integer getQuantityStudents();

    @Query(nativeQuery = true, value = "select avg(age) from students")
    Double getAverageAge();

    @Query(nativeQuery = true, value = "select * from students order by id desc limit 5")
    Collection<Student> getLastFiveStudents();
}
