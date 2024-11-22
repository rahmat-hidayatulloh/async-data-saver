package com.tscode.service;

import com.tscode.adaptor.ExternalEmployeeServiceAdaptor;
import com.tscode.common.constant.MessageConstants;
import com.tscode.exception.BusinessException;
import com.tscode.model.dto.EmployeeDto;
import com.tscode.model.dto.EmployeeLIstDto;
import com.tscode.model.entity.Employee;
import com.tscode.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@Slf4j
public class EmployeeService {

    @Inject
    @RestClient
    ExternalEmployeeServiceAdaptor externalEmployeeServiceAdaptor;

    @Inject
    EmployeeRepository employeeRepository;

    public CompletableFuture<Void> fetchAndSaveEmployee(Long employeeId) {
        return CompletableFuture.runAsync(() -> {

            validateEmployeeId(employeeId);

            EmployeeDto employeeDto = fetchEmployeeFromAdaptor(employeeId, new EmployeeDto());
            saveEmployee(mapDtoToEntity(employeeDto));

        });
    }

    public CompletableFuture<Void> saveEmployeeList() {
        return CompletableFuture.runAsync(() -> {

            for (EmployeeDto dto : fetchEmployeeListFromAdaptor(new EmployeeLIstDto())) {
                saveEmployee(mapDtoToEntity(dto));
            }
        });
    }

    private Employee mapDtoToEntity(EmployeeDto dto) {
        return Employee.builder()
                .employeeId(dto.getEmployeeId())
                .employeeName(dto.getEmployeeName())
                .managerName(dto.getManagerName())
                .pathLevel(dto.getPathLevel())
                .employeeFormat(dto.getEmployeeFormat())
                .pathHierarchy(dto.getPathHierarchy())
                .build();
    }

    private EmployeeDto fetchEmployeeFromAdaptor(Long employeeId, EmployeeDto employeeDto) {

        try {
            employeeDto = externalEmployeeServiceAdaptor.getSourceEmployee(employeeId);
            if (employeeDto == null) {
                throw new BusinessException(String.format(MessageConstants.EMPLOYEE_FETCH_FAILED, employeeId));
            }
            return employeeDto;
        } catch (Exception e){
            log.error("Error fetching employee with ID {}: {}", employeeId, e.getMessage());
            throw new RuntimeException(MessageConstants.EMPLOYEE_FETCH_FAILED, e);
        }
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0)
            throw new BusinessException(String.format(MessageConstants.INVALID_EMPLOYEE_ID, employeeId));
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.persist(employee);
    }

    private List<EmployeeDto> fetchEmployeeListFromAdaptor(EmployeeLIstDto employees) {

        employees = externalEmployeeServiceAdaptor.getSourceEmployeeList();
        if (employees == null || employees.getEmployees().isEmpty()) {
            throw new BusinessException(MessageConstants.EMPLOYEE_LIST_EMPTY);
        }
        return employees.getEmployees();
    }
}
