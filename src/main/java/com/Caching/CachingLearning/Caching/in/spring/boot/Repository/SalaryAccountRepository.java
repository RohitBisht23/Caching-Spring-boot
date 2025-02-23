package com.Caching.CachingLearning.Caching.in.spring.boot.Repository;

import com.Caching.CachingLearning.Caching.in.spring.boot.Entity.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SalaryAccountRepository extends JpaRepository<SalaryAccount, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<SalaryAccount> findById(Long id);
}
