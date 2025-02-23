package com.Caching.CachingLearning.Caching.in.spring.boot.Service.Impl;

import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.Employee;
import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;
import com.Caching.CachingLearning.Caching.in.spring.boot.Exception.ResourceNotFoundException;
import com.Caching.CachingLearning.Caching.in.spring.boot.Repository.SalaryAccountRepository;
import com.Caching.CachingLearning.Caching.in.spring.boot.Service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
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

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalaryAccount increseSalary(Long accountId) {
        SalaryAccount account = salaryAccountRepository.findById(accountId).orElseThrow(
                () -> new RuntimeException("Account does not exist with id :"+accountId)
        );

        BigDecimal prevBalance = account.getBalance();
        BigDecimal newBalance = prevBalance.add(BigDecimal.valueOf(1000L));

        account.setBalance(newBalance);
        SalaryAccount savedSalaryAccount = salaryAccountRepository.save(account);
        return account;

    }
}
