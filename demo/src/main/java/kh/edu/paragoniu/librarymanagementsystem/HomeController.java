package kh.edu.paragoniu.librarymanagementsystem;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/health")
    public Map<String, String> home() {
        return Map.of("message", "Library API is running");
    }
}
