package com.cmd.spenditapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HealthService {

    @Autowired
    HealthService(){}
     boolean isHealthy(){
        true
    }
}
