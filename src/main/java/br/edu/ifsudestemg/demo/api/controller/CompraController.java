package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CompraDTO;
import br.edu.ifsudestemg.demo.model.entity.Compra;
import br.edu.ifsudestemg.demo.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compra")
@CrossOrigin
@RequiredArgsConstructor
public class CompraController {
    private final CompraService service;

    @GetMapping
    public ResponseEntity get(){
        List<Compra> compra = service.getCompra();
        return ResponseEntity.ok(compra.stream().map(CompraDTO ::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Compra> compra = service.getCompraById(id);
        if(!compra.isPresent()){
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CompraDTO dto){
        Compra compra = converter(dto);
        compra = service.salvar(compra);
        return new ResponseEntity(compra, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity put(@PathVariable("id") Long id,@RequestBody CompraDTO dto){
        if(!service.getCompraById(id).isPresent()){
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        Compra compra = converter(dto);
        compra.setId(id);
        service.salvar(compra);
        return ResponseEntity.ok(compra);
    }

    public Compra converter(CompraDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        return compra;
    }


}
