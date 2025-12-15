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
     * @param threshold The cutoff time. Any monitor with a latest heartbeat BEFORE this is considered dead.
     * @return List of dead monitors.
     * @implNote MVP Implementation: Fetches all records and filters in memory.
     * PERFORMANCE BOTTLENECK: This will degrade as table size grows.
     * TODO (Roadmap v2): Refactor to use JPQL/Native Query for database-side filtering.
     */
    public List<MonitorStatusResponse> getDeadMonitors(LocalDateTime threshold) {
        // 1. Fetch ALL heartbeats (Warning: Expensive operation in Prod)
        List<MonitorHeartbeat> allHeartbeats = repository.findAll();

        // 2. Group by MonitorID and find the LATEST heartbeat for each
        Map<String, MonitorHeartbeat> latestHeartbeats = allHeartbeats.stream()
                .collect(Collectors.toMap(
                        MonitorHeartbeat::getMonitorId,
                        Function.identity(),
                        (existing, replacement) -> existing.getTimestamp().isAfter(replacement.getTimestamp()) ? existing : replacement
                ));

        // 3. Filter: Keep only those strictly OLDER than the threshold
        return latestHeartbeats.values().stream()
                .filter(heartbeat -> heartbeat.getTimestamp().isBefore(threshold))
                .map(h -> new MonitorStatusResponse(
                        h.getMonitorId(),
                        "DOWN",
                        h.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}