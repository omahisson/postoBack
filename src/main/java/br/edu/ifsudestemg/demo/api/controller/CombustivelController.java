package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CombustivelDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.service.CombustivelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/combustivel")
@RequiredArgsConstructor
@CrossOrigin
public class CombustivelController {
    private final CombustivelService service;
    private final EntityReferenceResolver references;

    @GetMapping
    public ResponseEntity<List<CombustivelDTO>> get() {
        List<Combustivel> combustiveis = service.getCombustivel();
        return ResponseEntity.ok(combustiveis.stream().map(CombustivelDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Combustivel> combustivel = service.getCombustivelById(id);
        if (combustivel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Combustivel nao encontrado");
        }
        return ResponseEntity.ok(combustivel.map(CombustivelDTO::create));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody CombustivelDTO dto) {
        try {
            Combustivel combustivel = converter(dto);
            combustivel.setId(null);
            combustivel = service.salvar(combustivel);
            return ResponseEntity.status(HttpStatus.CREATED).body(CombustivelDTO.create(combustivel));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody CombustivelDTO dto) {
        if (service.getCombustivelById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Combustivel nao encontrado");
        }
        try {
            Combustivel combustivel = converter(dto);
            combustivel.setId(id);
            combustivel = service.salvar(combustivel);
            return ResponseEntity.ok(CombustivelDTO.create(combustivel));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        Optional<Combustivel> combustivel = service.getCombustivelById(id);
        if (combustivel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Combustivel nao encontrado");
        }
        try {
            service.excluir(combustivel.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Combustivel converter(CombustivelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Combustivel combustivel = modelMapper.map(dto, Combustivel.class);
        combustivel.setPosto(references.buscarPosto(dto.getIdPosto()));
        return combustivel;
    }
}
