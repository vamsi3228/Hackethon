package com.azenio.hackathon.controller;

import com.azenio.hackathon.entity.Alert;
import com.azenio.hackathon.entity.Case;
import com.azenio.hackathon.repository.AlertRepository;
import com.azenio.hackathon.repository.CaseRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cases")
public class CaseController {

    private final CaseRepository caseRepository;
    private final AlertRepository alertRepository;

    public CaseController(
            CaseRepository caseRepository,
            AlertRepository alertRepository) {
        this.caseRepository = caseRepository;
        this.alertRepository = alertRepository;
    }

    @PostMapping
    public Case createCase(
            @RequestParam Long alertId,
            @RequestParam String analyst) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new RuntimeException("Alert not found"));

        Case caseEntity = new Case();

        caseEntity.setAlert(alert);
        caseEntity.setStatus("OPEN");
        caseEntity.setAnalyst(analyst);
        caseEntity.setCreatedAt(LocalDateTime.now());
        caseEntity.setUpdatedAt(LocalDateTime.now());

        return caseRepository.save(caseEntity);
    }

    @PutMapping("/{id}/disposition")
    public Case dispositionCase(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam String reason) {

        Case caseEntity = caseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Case not found"));

        caseEntity.setStatus(status);
        caseEntity.setDispositionReason(reason);
        caseEntity.setUpdatedAt(LocalDateTime.now());

        return caseRepository.save(caseEntity);
    }

    @GetMapping
    public List<Case> getCases() {
        return caseRepository.findAll();
    }
}