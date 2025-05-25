package com.example.planner.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "할일 내용은 필수입니다.")
    @Size(max = 200, message = "할일 내용은 최대 200자 이내여야 합니다.")
    private String contents;

    @NotBlank(message = "작성자명은 필수입니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
