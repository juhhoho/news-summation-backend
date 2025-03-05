package _2024.winter.newssummation.domain.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/api/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
