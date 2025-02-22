package com.Caching.CachingLearning.Caching.in.spring.boot.Service.Impl;


import com.Caching.CachingLearning.Caching.in.spring.boot.Dto.EmployeeDto;
import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.Employee;
import com.Caching.CachingLearning.Caching.in.spring.boot.Exception.ResourceNotFoundException;
import com.Caching.CachingLearning.Caching.in.spring.boot.Repository.EmployeeRepository;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.EmployeeService;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final SalaryAccountService salaryAccountService;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "employees";


    @Override
    public List<EmployeeDto> getAllEmployees() {
        log.info("Fetching all employee in the list");
        return repository.findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    @Transactional
    public EmployeeDto createNewEmployee(EmployeeDto newEmployee) {
        log.info("Checking if employee present with the same email");

        List<Employee> employeesExist = repository.findByEmail(newEmployee.getEmail());

        if(!employeesExist.isEmpty()) {
            log.info("employee not present with email :{}", newEmployee.getEmail());
            throw new RuntimeException("Employee exist with the email id :"+newEmployee.getEmail());
        }

        Employee newEmployeeToBeSave = modelMapper.map(newEmployee, Employee.class);
        log.info("Saving new Employee in the database");
        Employee savedEmployee = repository.save(newEmployeeToBeSave);

        //Creating salary account of the employee
        salaryAccountService.createAccount(savedEmployee);

        log.info("Returning back the saved employee");
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto getEmployeeById(Long id) {
        log.info("Fetching the employee with id {}", id);
        Employee employee = repository.findById(id).orElse(null);

        if(employee == null) {
            log.info("Employee not found");
            throw new ResourceNotFoundException("Employee with give id not found");
        }
        log.info("Employee found with the given id, now returning");
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void deleteEmployeeById(Long id) {
        log.info("Fetching the employee with id {} that need to be delete", id);
        Employee employee = repository.findById(id).orElse(null);

        if(employee == null) {
            log.info("Employee not found with given id");
            throw new ResourceNotFoundException("Employee with give id not found");
        }
        log.info("employee found now deleting");
        repository.deleteById(id);
        return;
    }

    @Override
    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public EmployeeDto updateEmployeeById(Long id, EmployeeDto update) {
        log.info("updating employee with id : {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> {
                    log.info("Employee not found with id :{}", id);
                    return new ResourceNotFoundException("employee with given id not exist in the database");
                });

        if(!employee.getEmail().equals(update.getEmail())) {
            log.error("attempted to update email for employee with id :{}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }
        modelMapper.map(update, employee);
        employee.setId(id);
        Employee updatedEmployee = repository.save(employee);
        log.info("Successfully update employee");
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
