package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {
    private final DashboardService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> get(@RequestParam(value = "idPosto", required = false) Long idPosto) {
        return ResponseEntity.ok(service.getDashboard(idPosto));
    }
}
