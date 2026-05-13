package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.PromocaoDTO;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.service.PromocaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/promocao")
@RequiredArgsConstructor
@CrossOrigin
public class PromocaoController {

    private final PromocaoService service;

    @GetMapping
    public ResponseEntity get(){
        List<Promocao> promocao = service.getPromocao();
        return ResponseEntity.ok(promocao.stream().map(PromocaoDTO ::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Promocao> promocao = service.getPromocaoById(id);
        if(!promocao.isPresent()){
            return new ResponseEntity("Promoção não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(promocao.map(PromocaoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody PromocaoDTO dto){
        Promocao promocao = converter(dto);
        promocao = service.salvar(promocao);
        return new ResponseEntity(promocao, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody PromocaoDTO dto){
        if(!service.getPromocaoById(id).isPresent()){
            return new ResponseEntity("Promoção não encontrada", HttpStatus.NOT_FOUND);
        }
        Promocao promocao = converter(dto);
        promocao.setId(id);
        service.salvar(promocao);
        return ResponseEntity.ok(promocao);
    }

    public Promocao converter(PromocaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Promocao promocao = modelMapper.map(dto, Promocao.class);
        return promocao;
    }
}
