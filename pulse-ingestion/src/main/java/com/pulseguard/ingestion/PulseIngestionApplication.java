package com.pulseguard.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PulseIngestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PulseIngestionApplication.class, args);
    }
}