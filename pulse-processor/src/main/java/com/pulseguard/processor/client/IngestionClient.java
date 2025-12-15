package com.pulseguard.processor.client;

import com.pulseguard.processor.dto.MonitorStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "pulse-ingestion")
public interface IngestionClient {

    @GetMapping("/api/v1/ingestion/dead-monitors")
    List<MonitorStatus> getDeadMonitors();
}