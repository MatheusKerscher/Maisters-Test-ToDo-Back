package com.br.matheus.kerscher.todolist.todo_list.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class BrazilTime {
    public static LocalDateTime findCurrentTime() {
        return ZonedDateTime.now(ZoneOffset.ofHours(-3)).toLocalDateTime();
    }
}
