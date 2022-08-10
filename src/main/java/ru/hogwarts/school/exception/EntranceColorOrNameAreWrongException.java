package ru.hogwarts.homeworks4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntranceColorOrNameAreWrongException extends RuntimeException{
}
