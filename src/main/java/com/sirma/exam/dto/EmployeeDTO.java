package com.sirma.exam.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;
import org.jetbrains.annotations.NotNull;


import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotNull
    @Positive(message = "empId1 must be positive")
    private Long empId1;

    @NotNull
    @Positive(message = "empId2 must be positive")
    private Long empId2;

    @NotNull
    @Positive(message = "projectId must be positive")
    private Long projectId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Positive(message = "overlapDays must be positive")
    private long overlapDays;

}
