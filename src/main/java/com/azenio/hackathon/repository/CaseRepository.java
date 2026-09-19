package com.azenio.hackathon.repository;

import com.azenio.hackathon.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<Case, Long> {
}