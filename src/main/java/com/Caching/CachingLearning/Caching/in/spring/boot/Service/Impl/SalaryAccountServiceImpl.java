package com.Caching.CachingLearning.Caching.in.spring.boot.Service.Impl;

import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.Employee;
import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;
import com.Caching.CachingLearning.Caching.in.spring.boot.Repository.SalaryAccountRepository;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public void createAccount(Employee employee) {
        if(employee.getName().equals("Rohit Bisht")) {
            throw new RuntimeException("Employee with same email already exists");
        }

        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);
    }
}
