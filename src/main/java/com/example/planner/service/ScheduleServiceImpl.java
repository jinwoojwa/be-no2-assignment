package com.example.planner.service;

import com.example.planner.dto.ScheduleRequestDto;
import com.example.planner.dto.ScheduleResponseDto;
import com.example.planner.entity.Schedule;
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
        Schedule schedule = new Schedule(dto.getAuthor(), dto.getContents(), dto.getPassword(), LocalDateTime.now(), LocalDateTime.now());

        return scheduleRepository.createSchedule(schedule);
    }

    @Override
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedule() {
        List<ScheduleResponseDto> schedules = scheduleRepository.getAllSchedule(); // repo에서 조회
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The content and author are required values");
        }

        // 수정 시점의 시간
        LocalDateTime modifiedDate = LocalDateTime.now();

        int updatedRow = scheduleRepository.updateSchedule(
                id,
                dto.getContents(),
                dto.getAuthor(),
                modifiedDate
        );

        if (updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        Schedule schedule = scheduleRepository.getScheduleById(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been deleted.");
        }
    }
}
