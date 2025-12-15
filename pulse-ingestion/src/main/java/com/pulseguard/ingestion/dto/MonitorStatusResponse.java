package com.pulseguard.ingestion.dto;

import java.time.LocalDateTime;

public record MonitorStatusResponse(
        String monitorId,
        String status,
        LocalDateTime lastHeartbeat
) {
}