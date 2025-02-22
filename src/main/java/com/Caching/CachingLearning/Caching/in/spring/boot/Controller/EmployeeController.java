package com.Caching.CachingLearning.Caching.in.spring.boot.Controller;


import com.Caching.CachingLearning.Caching.in.spring.boot.Dto.EmployeeDto;
import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.EmployeeService;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.SalaryAccountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SalaryAccountService salaryAccountService;

//    public EmployeeController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }

    @GetMapping("/getAllEmployee")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping("/createNewEmployee")
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto newEmployee){
        return new ResponseEntity<>(employeeService.createNewEmployee(newEmployee), HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PutMapping("/updateEmployeeById/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@RequestBody EmployeeDto update, @PathVariable Long id) {
        return new ResponseEntity<>(employeeService.updateEmployeeById(id, update), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Employee is successfully deleted.", HttpStatus.OK);

    }

    @PutMapping("/incrementBalance/{accountId}")
    public ResponseEntity<SalaryAccount> incrementBalance(@PathVariable Long accountId) {
        return ResponseEntity.ok(salaryAccountService.increseSalary(accountId));
    }
}
