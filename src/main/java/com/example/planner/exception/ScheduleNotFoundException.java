package com.example.planner.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {
        super("해당 일정이 존재하지 않습니다. id = " + id);
    }
}
