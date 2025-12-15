package com.pulseguard.ingestion.repository;

import com.pulseguard.ingestion.entity.MonitorHeartbeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartbeatRepository extends JpaRepository<MonitorHeartbeat, Long> {
    @Query("SELECT h FROM MonitorHeartbeat h WHERE h.timestamp < :threshold")
    List<MonitorHeartbeat> findOlderThan(LocalDateTime threshold);
}