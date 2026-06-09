package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CompraDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
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
    private final EntityReferenceResolver references;

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
        compra.setId(null);
        compra = service.salvar(compra);
        return new ResponseEntity(CompraDTO.create(compra), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id,@RequestBody CompraDTO dto){
        if(!service.getCompraById(id).isPresent()){
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        Compra compra = converter(dto);
        compra.setId(id);
        compra = service.salvar(compra);
        return ResponseEntity.ok(CompraDTO.create(compra));
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.getCompraById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra converter(CompraDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        compra.setPosto(references.buscarPosto(dto.getIdPosto()));
        return compra;
    }


}
