package com.pulseguard.ingestion.repository;

import com.pulseguard.ingestion.entity.MonitorHeartbeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartbeatRepository extends JpaRepository<MonitorHeartbeat, Long> {
}