package com.br.matheus.kerscher.todolist.todo_list.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
