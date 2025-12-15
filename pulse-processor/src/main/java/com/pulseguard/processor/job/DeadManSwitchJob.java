package com.pulseguard.processor.job;

import com.pulseguard.processor.client.IngestionClient;
import com.pulseguard.processor.dto.MonitorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeadManSwitchJob {

    private final IngestionClient ingestionClient;

    @Scheduled(fixedRate = 30000)
    public void checkMonitors() {
        log.info("Running Dead Man's Switch Check...");

        try {
            // Synchronous call to Ingestion Service via Feign
            List<MonitorStatus> deadMonitors = ingestionClient.getDeadMonitors();

            if (deadMonitors.isEmpty()) {
                log.info("All monitors are healthy.");
                return;
            }

            // In real app would send an email/Slack message
            deadMonitors.forEach(monitor -> {
                log.error("ALARM! Monitor [{}] is DOWN. Last seen: {}",
                        monitor.monitorId(), monitor.lastHeartbeat());
            });

        } catch (Exception e) {
            log.error("Failed to contact Ingestion Service", e);
        }
    }
}