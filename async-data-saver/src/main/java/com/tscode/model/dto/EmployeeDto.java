package com.tscode.model.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    public Long employeeId;
    public String employeeName;
    public String managerName;
    public String employeeFormat;
    public String pathHierarchy;
    public Integer pathLevel;
}
