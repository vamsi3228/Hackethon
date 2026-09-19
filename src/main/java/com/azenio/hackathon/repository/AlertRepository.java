package com.azenio.hackathon.repository;

import com.azenio.hackathon.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}