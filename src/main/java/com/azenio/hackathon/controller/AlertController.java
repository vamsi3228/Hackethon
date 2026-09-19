package com.azenio.hackathon.controller;

import com.azenio.hackathon.entity.Alert;
import com.azenio.hackathon.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAlerts() {
        return alertService.getAllAlerts();
    }
}