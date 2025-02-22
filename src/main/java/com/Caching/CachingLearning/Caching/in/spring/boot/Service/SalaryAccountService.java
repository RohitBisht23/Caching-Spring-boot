package com.Caching.CachingLearning.Caching.in.spring.boot.Service;

import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.Employee;
import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount increseSalary(Long accountId);
}
