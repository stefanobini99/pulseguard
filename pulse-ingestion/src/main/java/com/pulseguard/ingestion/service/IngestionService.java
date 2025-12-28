package com.pulseguard.ingestion.service;

import com.pulseguard.ingestion.dto.HeartbeatRequest;
import com.pulseguard.ingestion.dto.MonitorStatusResponse;
import com.pulseguard.ingestion.entity.MonitorHeartbeat;
import com.pulseguard.ingestion.repository.HeartbeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * Retrieves monitors that have not sent a heartbeat since the threshold time.
     *
     * @param threshold The cutoff time. Any monitor with latest heartbeat BEFORE this is considered dead.
     * @return List of dead monitors.
     */
    public List<MonitorStatusResponse> getDeadMonitors(LocalDateTime threshold) {
        return repository.findDeadMonitors(threshold).stream()
                .map(h -> new MonitorStatusResponse(h.getMonitorId(), "DOWN", h.getTimestamp()))
                .collect(Collectors.toList());
    }
}