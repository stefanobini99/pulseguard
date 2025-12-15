package com.pulseguard.ingestion.controller;

import com.pulseguard.ingestion.dto.HeartbeatRequest;
import com.pulseguard.ingestion.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}