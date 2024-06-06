package com.br.matheus.kerscher.todolist.todo_list.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.br.matheus.kerscher.todolist.todo_list.utils.BrazilTime.findCurrentTime;

@ControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandarError> entityNotFoundHandlerMethod(EntityNotFoundException e, HttpServletRequest request) {
        StandarError error = new StandarError(
                findCurrentTime(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandarError> conflictHandlerMethod(ConflictException e, HttpServletRequest request) {
        StandarError error = new StandarError(
                findCurrentTime(),
                HttpStatus.CONTINUE.value(),
                HttpStatus.CONTINUE.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
