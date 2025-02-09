package com.Caching.CachingLearning.Caching.in.spring.boot.Service;

import com.Caching.CachingLearning.Caching.in.spring.boot.Dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    EmployeeDto createNewEmployee(EmployeeDto newEmployee);

    EmployeeDto getEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    EmployeeDto updateEmployeeById(Long id, EmployeeDto update);
}
