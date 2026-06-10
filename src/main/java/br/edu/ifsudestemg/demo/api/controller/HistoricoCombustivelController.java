package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.HistoricoCombustivelDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.HistoricoCombustivel;
import br.edu.ifsudestemg.demo.service.CombustivelService;
import br.edu.ifsudestemg.demo.service.HistoricoCombustivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/historico-combustivel")
@RequiredArgsConstructor
@CrossOrigin
public class HistoricoCombustivelController {
    private final HistoricoCombustivelService service;
    private final CombustivelService combustivelService;

    @GetMapping
    public ResponseEntity<List<HistoricoCombustivelDTO>> listar(
            @RequestParam(value = "idPosto", required = false) Long idPosto,
            @RequestParam(value = "idCombustivel", required = false) Long idCombustivel,
            @RequestParam(value = "tipoCombustivelId", required = false) Long tipoCombustivelId) {
        Long combustivelId = idCombustivel == null ? tipoCombustivelId : idCombustivel;
        List<HistoricoCombustivel> historicos = service.getHistoricos(idPosto, combustivelId);
        return ResponseEntity.ok(historicos.stream().map(HistoricoCombustivelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<HistoricoCombustivel> historico = service.getHistoricoById(id);
        if (historico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historico nao encontrado");
        }
        return ResponseEntity.ok(historico.map(HistoricoCombustivelDTO::create));
    }

    @PostMapping("/alterar-preco")
    public ResponseEntity<?> alterarPreco(@RequestBody HistoricoCombustivelDTO dto) {
        try {
            Combustivel combustivel = buscarCombustivel(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(HistoricoCombustivelDTO.create(service.alterarPreco(combustivel, dto)));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<HistoricoCombustivel> historico = service.getHistoricoById(id);
        if (historico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historico nao encontrado");
        }
        try {
            service.excluir(historico.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Combustivel buscarCombustivel(HistoricoCombustivelDTO dto) {
        Long idCombustivel = dto.getIdCombustivel() == null ? dto.getTipoCombustivelId() : dto.getIdCombustivel();
        if (idCombustivel == null) {
            throw new RegraNegocioException("Combustivel deve ser informado");
        }
        return combustivelService.getCombustivelById(idCombustivel)
                .orElseThrow(() -> new RegraNegocioException("Combustivel nao encontrado"));
    }
}
