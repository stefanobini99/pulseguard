package com.pulseguard.ingestion.repository;

import com.pulseguard.ingestion.entity.MonitorHeartbeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartbeatRepository extends JpaRepository<MonitorHeartbeat, Long> {
    @Query("SELECT h FROM MonitorHeartbeat h WHERE h.timestamp < :threshold")
    List<MonitorHeartbeat> findOlderThan(LocalDateTime threshold);

    @Query("SELECT h FROM MonitorHeartbeat h " +
            "WHERE h.timestamp = (" +
            "   SELECT MAX(h2.timestamp) " +
            "   FROM MonitorHeartbeat h2 " +
            "   WHERE h2.monitorId = h.monitorId" +
            ") " +
            "AND h.timestamp < :threshold")
    List<MonitorHeartbeat> findDeadMonitors(@Param("threshold") LocalDateTime threshold);
}