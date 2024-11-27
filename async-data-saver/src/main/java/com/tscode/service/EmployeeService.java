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

    public CompletableFuture<EmployeeDto> fetchAndSaveEmployee(Long employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                validateEmployeeId(employeeId);
                EmployeeDto employeeDto = fetchEmployeeFromAdaptor(employeeId);
                saveEmployee(mapDtoToEntity(employeeDto));
                return employeeDto;
            } catch (Exception e) {
                log.error("Error in fetchAndSaveEmployee: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<List<EmployeeDto>> saveEmployeeList() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<EmployeeDto> employeeDtos = fetchEmployeeListFromAdaptor();
                employeeDtos.forEach(dto -> saveEmployee(mapDtoToEntity(dto)));
                return employeeDtos;
            } catch (Exception e) {
                log.error("Error in saveEmployeeList: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }

    public Employee findEmployeeById(Long employeeId) {
        validateEmployeeId(employeeId);
        return employeeRepository.findByIdOptional(employeeId)
                .orElseThrow(() -> new BusinessException(String.format("Employee with ID %d not found", employeeId)));
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

    private EmployeeDto fetchEmployeeFromAdaptor(Long employeeId) {
        try {
            EmployeeDto employeeDto = externalEmployeeServiceAdaptor.getSourceEmployee(employeeId);
            if (employeeDto == null) {
                throw new BusinessException(String.format(MessageConstants.EMPLOYEE_FETCH_FAILED, employeeId));
            }
            return employeeDto;
        } catch (Exception e) {
            log.error("Error fetching employee from adaptor with ID {}: {}", employeeId, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private List<EmployeeDto> fetchEmployeeListFromAdaptor() {
        try {
            EmployeeLIstDto employees = externalEmployeeServiceAdaptor.getSourceEmployeeList();
            if (employees == null || employees.getEmployees().isEmpty()) {
                throw new BusinessException(MessageConstants.EMPLOYEE_LIST_EMPTY);
            }
            return employees.getEmployees();
        } catch (Exception e) {
            log.error("Error fetching employee list from adaptor: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new BusinessException(String.format(MessageConstants.INVALID_EMPLOYEE_ID, employeeId));
        }
    }

    @Transactional
    public void saveEmployee(Employee employee) {
        try {
            employeeRepository.persist(employee);
            log.info("Employee with ID {} saved successfully", employee.getEmployeeId());
        } catch (Exception e) {
            log.error("Error saving employee with ID {}: {}", employee.getEmployeeId(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}

