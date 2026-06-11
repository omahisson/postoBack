package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.PdvFechamentoDTO;
import br.edu.ifsudestemg.demo.api.dto.PdvTurnoDTO;
import br.edu.ifsudestemg.demo.api.dto.PdvVendaDTO;
import br.edu.ifsudestemg.demo.service.PdvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pdv")
@RequiredArgsConstructor
@CrossOrigin
public class PdvController {
    private final PdvService service;

    @PostMapping("/turnos")
    public ResponseEntity<PdvTurnoDTO> abrirTurno(@RequestBody PdvTurnoDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PdvTurnoDTO.create(service.abrirTurno(payload)));
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity<PdvTurnoDTO> buscarTurno(@PathVariable Long id) {
        return ResponseEntity.ok(PdvTurnoDTO.create(service.buscarTurno(id)));
    }

    @GetMapping("/turnos")
    public ResponseEntity<List<PdvTurnoDTO>> buscarTurnos(@RequestParam Long idPosto, @RequestParam(defaultValue = "aberto") String status) {
        if (!"aberto".equalsIgnoreCase(status)) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(service.buscarTurnosAbertos(idPosto).stream().map(PdvTurnoDTO::create).toList());
    }

    @PutMapping("/turnos/{id}/fechar")
    public ResponseEntity<PdvTurnoDTO> fecharTurno(@PathVariable Long id, @RequestBody PdvFechamentoDTO payload) {
        return ResponseEntity.ok(PdvTurnoDTO.create(service.fecharTurno(id, payload)));
    }

    @GetMapping("/turnos/{id}/vendas")
    public ResponseEntity<List<PdvVendaDTO>> listarVendasDoTurno(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarVendasDoTurno(id).stream().map(PdvVendaDTO::create).toList());
    }

    @PostMapping("/vendas")
    public ResponseEntity<PdvVendaDTO> registrarVenda(@RequestBody PdvVendaDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PdvVendaDTO.create(service.registrarVenda(payload)));
    }

    @GetMapping("/vendas/{id}")
    public ResponseEntity<PdvVendaDTO> buscarVenda(@PathVariable Long id) {
        return ResponseEntity.ok(PdvVendaDTO.create(service.buscarVenda(id)));
    }

    @PutMapping("/vendas/{id}/cancelar")
    public ResponseEntity<PdvVendaDTO> cancelarVenda(@PathVariable Long id, @RequestBody(required = false) PdvVendaDTO payload) {
        String motivo = payload == null ? null : payload.getMotivoCancelamento();
        return ResponseEntity.ok(PdvVendaDTO.create(service.cancelarVenda(id, motivo)));
    }
}
