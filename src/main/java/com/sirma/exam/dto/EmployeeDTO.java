package com.sirma.exam.dto;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long empId1;
    private Long empId2;
    private Long projectId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Duration duration;
    private long overlapDays;

}
