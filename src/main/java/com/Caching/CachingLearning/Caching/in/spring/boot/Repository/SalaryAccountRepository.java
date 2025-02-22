package com.Caching.CachingLearning.Caching.in.spring.boot.Repository;

import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalaryAccountRepository extends JpaRepository<SalaryAccount, Long> {
}
