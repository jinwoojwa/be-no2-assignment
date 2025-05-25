package com.example.planner.service;

import com.example.planner.dto.ScheduleRequestDto;
import com.example.planner.dto.ScheduleResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);

    // 모든 일정 조회
    ResponseEntity<List<ScheduleResponseDto>> getAllSchedule(String modifiedDate, String author, int page, int size);

    // 선택 일정 조회
    ScheduleResponseDto getScheduleById(Long id);

    // 선택 일정 수정
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);

    // 선택 일정 삭제
    void deleteSchedule(Long id, String password);
}
