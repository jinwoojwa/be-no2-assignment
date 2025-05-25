package com.example.planner.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule {

    private Long id;
    private String contents;
    private String author;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Schedule(String contents, String author, String password, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.contents = contents;
        this.author = author;
        this.password = password;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Schedule(Long id, String contents, String author, String password, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.contents = contents;
        this.author = author;
        this.password = password;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
