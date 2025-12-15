package com.pulseguard.ingestion.controller;

import com.pulseguard.ingestion.dto.HeartbeatRequest;
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
    public List<HeartbeatRequest> getDeadMonitors() {
        // Logic: Define "Dead" as not seen in 60 seconds
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(60);

        // In a real app, you would group by monitorId and pick the max timestamp
        // Since this is only as portfolio it returns the raw list for the Processor to handle
        // ... implementation logic ...
        return List.of(); // Placeholder to make it compile
    }
}