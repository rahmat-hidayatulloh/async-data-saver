package com.tscode.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    @NotBlank(message = "employeeId may not be blank")
    public Long employeeId;

    @NotBlank(message = "employeeName may not be blank")
    public String employeeName;

    @NotBlank(message = "managerName may not be blank")
    public String managerName;

    @NotBlank(message = "employeeFormat may not be blank")
    public String employeeFormat;

    @NotBlank(message = "pathHierarchy may not be blank")
    public String pathHierarchy;

    @NotBlank(message = "pathLevel may not be blank")
    public Integer pathLevel;
}
