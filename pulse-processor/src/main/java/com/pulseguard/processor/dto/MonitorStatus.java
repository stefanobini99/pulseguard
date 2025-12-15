package com.pulseguard.processor.dto;

import java.time.LocalDateTime;

public record MonitorStatus(String monitorId, String status, LocalDateTime lastHeartbeat) {
}