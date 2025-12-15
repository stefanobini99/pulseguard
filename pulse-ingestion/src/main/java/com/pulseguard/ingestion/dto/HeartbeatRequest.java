package com.pulseguard.ingestion.dto;

public record HeartbeatRequest(String monitorId, String status) {
}