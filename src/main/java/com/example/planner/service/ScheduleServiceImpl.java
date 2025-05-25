package com.example.planner.service;

import com.example.planner.dto.ScheduleRequestDto;
import com.example.planner.dto.ScheduleResponseDto;
import com.example.planner.entity.Schedule;
import com.example.planner.exception.InvalidRequestException;
import com.example.planner.exception.PasswordMismatchException;
import com.example.planner.exception.ScheduleNotFoundException;
import com.example.planner.repository.ScheduleRepository;
import com.example.planner.repository.ScheduleRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {

        // 요청받은 데이터로 스케줄 객체를 생성
        Schedule schedule = new Schedule(dto.getContents(), dto.getAuthor(), dto.getPassword(), LocalDateTime.now(), LocalDateTime.now());

        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedule(
            String modifiedDate, String author, int page, int size
    ) {
        List<ScheduleResponseDto> schedules = scheduleRepository.getAllSchedule(modifiedDate, author, page, size); // repo에서 조회
        return ResponseEntity.ok(schedules);
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {

        Schedule schedule = scheduleRepository.getScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {

        if (dto.getContents() == null || dto.getAuthor() == null) {
            throw new InvalidRequestException("The content and author are required values");
        }

        Schedule existingSchedule = scheduleRepository.getScheduleById(id);
        // password 검증
        if (!existingSchedule.getPassword().equals(dto.getPassword())) {
            throw new PasswordMismatchException();
        }

        LocalDateTime modifiedDate = LocalDateTime.now();
        int updatedRow = scheduleRepository.updateSchedule(
                id,
                dto.getContents(),
                dto.getAuthor(),
                modifiedDate
        );

        if (updatedRow == 0){
            throw new ScheduleNotFoundException(id);
        }

        Schedule schedule = scheduleRepository.getScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.getScheduleById(id);

        if (!schedule.getPassword().equals(password)) {
            throw new PasswordMismatchException();
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0) {
            throw new ScheduleNotFoundException(id);
        }
    }
}
