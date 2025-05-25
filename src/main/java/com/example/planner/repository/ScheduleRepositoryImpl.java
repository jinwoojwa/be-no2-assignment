package com.example.planner.repository;

import com.example.planner.dto.ScheduleResponseDto;
import com.example.planner.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("contents", schedule.getContents());
        parameters.put("author", schedule.getAuthor());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdDate", schedule.getCreatedDate());
        parameters.put("modifiedDate", schedule.getModifiedDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getContents(),
                schedule.getAuthor(),
                schedule.getCreatedDate(),
                schedule.getModifiedDate()
        );
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedule() {

        String sql = "SELECT * FROM SCHEDULE";

        return jdbcTemplate.query(sql, scheduleRowMapper());
    }

    @Override
    public Schedule getScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleRowMapper2(),
                id
        );
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String contents, String author, LocalDateTime modifiedDate) {
        return jdbcTemplate.update("UPDATE schedule SET contents = ?, author = ?, modified_date = ? WHERE id = ?", contents, author, modifiedDate, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getString("author"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("modified_date").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapper2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("contents"),
                        rs.getString("author"),
                        rs.getTimestamp("created_date").toLocalDateTime(),
                        rs.getTimestamp("modified_date").toLocalDateTime()
                );
            }
        };
    }
}
