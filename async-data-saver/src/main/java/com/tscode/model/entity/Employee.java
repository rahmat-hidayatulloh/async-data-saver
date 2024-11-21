package com.tscode.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employee extends PanacheEntity {
    public Long employeeId;
    public String employeeName;
    public String managerName;
    public String employeeFormat;
    public String pathHierarchy;
    public Integer pathLevel;
}