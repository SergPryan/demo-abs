package com.abs.demoabs.repository;

import com.abs.demoabs.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {
}
