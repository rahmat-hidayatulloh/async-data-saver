package com.tscode.service;

import com.tscode.adaptor.ExternalEmployeeServiceAdaptor;
import com.tscode.model.dto.EmployeeDto;
import com.tscode.model.dto.EmployeeLIstDto;
import com.tscode.model.entity.Employee;
import com.tscode.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@ApplicationScoped
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());

    @Inject
    @RestClient
    ExternalEmployeeServiceAdaptor employeeServiceAdaptor;

    @Inject
    EmployeeRepository employeeRepository;

    public CompletableFuture<Void> fetchAndSaveEmployee(Long employeeId) {
        return CompletableFuture.runAsync(() -> {
            try {
                EmployeeDto dto = employeeServiceAdaptor.getEmployee(employeeId);
                Employee employee = new Employee();
                employee.employeeId = dto.employeeId;
                employee.employeeName = dto.employeeName;
                employee.managerName = dto.managerName;
                employee.employeeFormat = dto.employeeFormat;
                employee.pathHierarchy = dto.pathHierarchy;
                employee.pathLevel = dto.pathLevel;
                saveEmployee(employee);
            } catch (Exception e) {
                // Handle the exception properly (e.g., log it or rethrow a custom exception)
                throw new RuntimeException("Failed to fetch and save employee", e);
            }
        });
    }


    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.persist(employee);
    }

    public CompletableFuture<Void> saveEmployeeList() {
        return CompletableFuture.runAsync(() -> {


            EmployeeLIstDto employees = employeeServiceAdaptor.postEmployeeList();

            LOGGER.info("Employees: " + employees.getList());
            System.out.println("employees : >>>>>" + employees.getList() );
            for (EmployeeDto dto : employees.getList()) {
                Employee employee = new Employee();
                employee.employeeId = dto.getEmployeeId();
                employee.employeeName = dto.getEmployeeName();
                employee.managerName = dto.getManagerName();
                employee.employeeFormat = dto.getEmployeeFormat();
                employee.pathHierarchy = dto.getPathHierarchy();
                employee.pathLevel = dto.getPathLevel();

                saveEmployee(employee);
            }
        });
    }
}
