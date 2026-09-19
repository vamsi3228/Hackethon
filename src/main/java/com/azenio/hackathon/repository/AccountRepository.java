package com.azenio.hackathon.repository;

import com.azenio.hackathon.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}