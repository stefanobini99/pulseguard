package com.pulseguard.ingestion.controller;

import com.pulseguard.ingestion.dto.HeartbeatRequest;
import com.pulseguard.ingestion.dto.MonitorStatusResponse;
import com.pulseguard.ingestion.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ingestion")
@RequiredArgsConstructor
public class IngestionController {

    private final IngestionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void ingest(@RequestBody HeartbeatRequest request) {
        service.processHeartbeat(request);
    }

    @GetMapping("/dead-monitors")
    public List<MonitorStatusResponse> getDeadMonitors() {
        // Business Rule: A monitor is "Dead" if we haven't heard from it in 60 seconds.
        // In a real app, this interval would be configurable per monitor.
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(60);

        return service.getDeadMonitors(threshold);
    }
}