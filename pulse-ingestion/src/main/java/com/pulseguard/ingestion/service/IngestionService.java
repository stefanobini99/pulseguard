package com.pulseguard.ingestion.service;

import com.pulseguard.ingestion.dto.HeartbeatRequest;
import com.pulseguard.ingestion.entity.MonitorHeartbeat;
import com.pulseguard.ingestion.repository.HeartbeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngestionService {

    private final HeartbeatRepository repository;

    public void processHeartbeat(HeartbeatRequest request) {
        log.info("Received heartbeat for monitor: {}", request.monitorId());

        MonitorHeartbeat entity = MonitorHeartbeat.builder()
                .monitorId(request.monitorId())
                .status(request.status())
                .timestamp(LocalDateTime.now())
                .build();

        repository.save(entity);
    }
}