package br.edu.ifsudestemg.demo.api.controller;


import br.edu.ifsudestemg.demo.api.dto.CombustivelDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.service.CombustivelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/combustivel")
@RequiredArgsConstructor
@CrossOrigin
public class CombustivelController {
    private final CombustivelService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Combustivel> combustiveis = service.getCombustivel();
        return ResponseEntity.ok(combustiveis.stream().map(CombustivelDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Combustivel> combustivel = service.getCombustivelById(id);
        if(!combustivel.isPresent()){
            return new ResponseEntity("Combustivel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(combustivel.map(CombustivelDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody CombustivelDTO dto) {
        try {
            Combustivel combustivel = converter(dto);
            combustivel = service.salvar(combustivel);
            return new ResponseEntity(combustivel, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CombustivelDTO dto) {
        if (!service.getCombustivelById(id).isPresent()) {
            return new ResponseEntity("Combustivel não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Combustivel combustivel = converter(dto);
            combustivel.setId(id);
            service.salvar(combustivel);
            return ResponseEntity.ok(combustivel);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Combustivel converter(CombustivelDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Combustivel combustivel = modelMapper.map(dto, Combustivel.class);
        return combustivel;
    }
}
