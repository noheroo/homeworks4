package ru.hogwarts.school.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentExceptionHandler(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Студент не найден");
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<String> handleFacultyExceptionHandler(FacultyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_PLAIN)
                .body("Факультет не найден");
    }

    @ExceptionHandler(EntranceAgesAreWrongException.class)
    public ResponseEntity<String> handleEntranceDataIsWrongExceptionHandler(EntranceAgesAreWrongException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Введены неправильные данные возраста для фильтрации");
    }

    @ExceptionHandler(EntranceColorOrNameAreWrongException.class)
    public ResponseEntity<String> handleEntranceColorOrNameAreWrongExceptionHandler(EntranceColorOrNameAreWrongException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Введены неправильные Имя или Цвет");
    }

    @ExceptionHandler(StudentWithoutFacultyException.class)
    public ResponseEntity<String> handleStudentWithoutFacultyExceptionHandler(StudentWithoutFacultyException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("У студента не введен факультет");
    }

    @ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<String> handleAvatarNotFoundExceptionHandler(AvatarNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Аватар не найден");
    }

    @ExceptionHandler(ExtensionIsNullException.class)
    public ResponseEntity<String> handleExtensionIsNullExceptionHandler(ExtensionIsNullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Загружаемый файл без расширения");
    }


}
