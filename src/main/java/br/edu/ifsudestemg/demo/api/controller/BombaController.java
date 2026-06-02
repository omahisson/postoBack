package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.BombaDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.service.BombaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

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

    @PostMapping()
    public ResponseEntity post(@RequestBody BombaDTO dto) {
        try {
            Bomba bomba = converter(dto);
            bomba = service.salvar(bomba);
            return new ResponseEntity(bomba, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody BombaDTO dto) {
        if (!service.getBombaById(id).isPresent()) {
            return new ResponseEntity("Bomba não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Bomba bomba = converter(dto);
            bomba.setId(id);
            service.salvar(bomba);
            return ResponseEntity.ok(bomba);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        Optional<Bomba> bomba = service.getBombaById(id);
        if (bomba.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bomba não encontrada");
        }
        try {
            service.excluir(bomba.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Bomba converter(BombaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Bomba bomba = modelMapper.map(dto, Bomba.class);
        return bomba;
    }
}