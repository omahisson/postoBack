package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.BombaDTO;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.service.BombaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bomba")
@RequiredArgsConstructor
@CrossOrigin
public class BombaController {

    private final BombaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Bomba> bombas = service.getBomba();
        return ResponseEntity.ok(
                bombas.stream()
                        .map(BombaDTO::create)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Bomba> bomba = service.getBombaById(id);

        if(!bomba.isPresent()){
            return new ResponseEntity("Bomba não encontrada", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(BombaDTO.create(bomba.get()));
    }
}