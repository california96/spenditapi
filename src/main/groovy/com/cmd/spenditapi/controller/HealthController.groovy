package com.cmd.spenditapi.controller

import com.cmd.spenditapi.services.HealthService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "General Operations", description = "For API Monitoring")
class HealthController {
    @Autowired
    HealthService healthService

    HealthController(HealthService healthService) {
        this.healthService = healthService;
    }
    @GetMapping("/health")
    ResponseEntity<String> checkHealth() {
        if (healthService.isHealthy()) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
        }
    }

}
