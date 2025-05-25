package com.example.planner.repository;

import com.example.planner.dto.ScheduleResponseDto;
import com.example.planner.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto createSchedule(Schedule schedule);

    List<ScheduleResponseDto> getAllSchedule();

    Schedule getScheduleById(Long id);

    int updateSchedule(Long id, String contents, String author, LocalDateTime modifiedDate);

    int deleteSchedule(Long id);
}
