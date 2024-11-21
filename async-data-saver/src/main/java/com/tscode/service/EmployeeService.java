package com.tscode.service;

import com.tscode.adaptor.ExternalEmployeeServiceAdaptor;
import com.tscode.model.dto.EmployeeDto;
import com.tscode.model.entity.Employee;
import com.tscode.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class EmployeeService {

    @Inject
    @RestClient
    ExternalEmployeeServiceAdaptor employeeServiceAdaptor;

    @Inject
    EmployeeRepository employeeRepository;

    public CompletableFuture<Void> fetchAndSaveEmployee(Long employeeId) {
        return CompletableFuture.runAsync(() -> {
            EmployeeDto dto = employeeServiceAdaptor.getEmployee(employeeId);
            Employee employee = new Employee();
            employee.employeeId = dto.employeeId;
            employee.employeeName = dto.employeeName;
            employee.managerName = dto.managerName;
            employee.employeeFormat = dto.employeeFormat;
            employee.pathHierarchy = dto.pathHierarchy;
            employee.pathLevel = dto.pathLevel;

            saveEmployee(employee);
        });
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.persist(employee);
    }

    public CompletableFuture<Void> saveEmployeeList() {
        return CompletableFuture.runAsync(() -> {
            List<EmployeeDto> employees = employeeServiceAdaptor.postEmployeeList();
            for (EmployeeDto dto : employees) {
                Employee employee = new Employee();
                employee.employeeId = dto.employeeId;
                employee.employeeName = dto.employeeName;
                employee.managerName = dto.managerName;
                employee.employeeFormat = dto.employeeFormat;
                employee.pathHierarchy = dto.pathHierarchy;
                employee.pathLevel = dto.pathLevel;

                saveEmployee(employee);
            }
        });
    }
}
