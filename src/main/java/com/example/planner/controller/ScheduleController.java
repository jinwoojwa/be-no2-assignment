package com.example.planner.controller;

import com.example.planner.dto.ScheduleRequestDto;
import com.example.planner.dto.ScheduleResponseDto;
import com.example.planner.exception.InvalidRequestException;
import com.example.planner.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 생성자 주입
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Lv 1. 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto) {

        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
    }

    // Lv 1. 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String modifiedDate,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,       // 페이지 번호
            @RequestParam(defaultValue = "10") int size       // 페이지 크기
    ) {
        return scheduleService.getAllSchedule(modifiedDate, author, page, size);
    }

    // Lv 1. 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto dto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidRequestException(errorMessage);
        }

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDto dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new InvalidRequestException(errorMessage);
        }

        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
