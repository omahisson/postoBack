package br.edu.ifsudestemg.demo.api.controller;


import br.edu.ifsudestemg.demo.api.dto.CombustivelDTO;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(combustivel.map(CombustivelDTO::create);
    }
}
