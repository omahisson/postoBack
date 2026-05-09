package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.BombaRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.BombaResponseDTO;
import br.edu.ifsudestemg.demo.service.BombaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bomba")
@RequiredArgsConstructor
@CrossOrigin
public class BombaController {
    private final BombaService service;

    @GetMapping()
    public ResponseEntity<List<BombaResponseDTO>> get(){
        List<BombaResponseDTO> bombas = service.getBombas();
        return ResponseEntity.ok(bombas);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BombaResponseDTO> get(@PathVariable Long id){
        BombaResponseDTO bomba = service.getBomba(id);
        return ResponseEntity.ok(bomba);
    }
    @PostMapping()
    public ResponseEntity<BombaResponseDTO> create(@RequestBody @Valid BombaRequestDTO request){
        BombaResponseDTO bomba = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bomba);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<BombaResponseDTO> update(@PathVariable Long id, @RequestBody BombaRequestDTO request){
        BombaResponseDTO bomba = service.update(id, request);
        return ResponseEntity.ok(bomba);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
