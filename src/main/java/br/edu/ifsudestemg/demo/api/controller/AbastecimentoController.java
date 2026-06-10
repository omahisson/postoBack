package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.AbastecimentoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Abastecimento;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.service.AbastecimentoService;
import br.edu.ifsudestemg.demo.service.CombustivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/abastecimento")
@RequiredArgsConstructor
@CrossOrigin
public class AbastecimentoController {
    private final AbastecimentoService service;
    private final CombustivelService combustivelService;
    private final EntityReferenceResolver references;

    @GetMapping
    public ResponseEntity<List<AbastecimentoDTO>> listar(
            @RequestParam(value = "idPosto", required = false) Long idPosto,
            @RequestParam(value = "idCombustivel", required = false) Long idCombustivel) {
        List<Abastecimento> abastecimentos = service.getAbastecimentos(idPosto, idCombustivel);
        return ResponseEntity.ok(abastecimentos.stream().map(AbastecimentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Abastecimento> abastecimento = service.getAbastecimentoById(id);
        if (abastecimento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento nao encontrado");
        }
        return ResponseEntity.ok(abastecimento.map(AbastecimentoDTO::create));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AbastecimentoDTO dto) {
        try {
            Abastecimento abastecimento = converter(dto);
            abastecimento.setId(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(AbastecimentoDTO.create(service.salvar(abastecimento)));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AbastecimentoDTO dto) {
        if (service.getAbastecimentoById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento nao encontrado");
        }
        try {
            Abastecimento abastecimento = converter(dto);
            abastecimento.setId(id);
            return ResponseEntity.ok(AbastecimentoDTO.create(service.salvar(abastecimento)));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Abastecimento> abastecimento = service.getAbastecimentoById(id);
        if (abastecimento.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Abastecimento nao encontrado");
        }
        try {
            service.excluir(abastecimento.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Abastecimento converter(AbastecimentoDTO dto) {
        Abastecimento abastecimento = new Abastecimento();
        abastecimento.setId(dto.getId());
        abastecimento.setPosto(references.buscarPosto(dto.getIdPosto()));
        abastecimento.setCombustivel(buscarCombustivel(dto));
        abastecimento.setTipoCombustivel(dto.getTipoCombustivel());
        abastecimento.setFornecedor(dto.getFornecedor());
        abastecimento.setQuantidade(dto.getQuantidade());
        abastecimento.setUnidade(dto.getUnidade());
        abastecimento.setNumeroNota(dto.getNumeroNota());
        abastecimento.setDataEntrega(dto.getDataEntrega());
        abastecimento.setDataValidade(dto.getDataValidade());
        abastecimento.setPrecoUnitario(dto.getPrecoUnitario());
        abastecimento.setValorTotal(dto.getValorTotal());
        return abastecimento;
    }

    private Combustivel buscarCombustivel(AbastecimentoDTO dto) {
        if (dto.getIdCombustivel() != null) {
            return combustivelService.getCombustivelById(dto.getIdCombustivel())
                    .orElseThrow(() -> new RegraNegocioException("Combustivel nao encontrado"));
        }
        List<Combustivel> combustiveis = combustivelService.getCombustivelByPosto(dto.getIdPosto()).stream()
                .filter(item -> item.getNome() != null && item.getNome().equalsIgnoreCase(dto.getTipoCombustivel()))
                .toList();
        if (combustiveis.isEmpty()) {
            throw new RegraNegocioException("Combustivel nao encontrado");
        }
        return combustiveis.get(0);
    }
}
