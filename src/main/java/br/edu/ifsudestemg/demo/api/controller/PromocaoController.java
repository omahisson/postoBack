package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.PromocaoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
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
    private final EntityReferenceResolver references;

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
        promocao.setId(null);
        promocao = service.salvar(promocao);
        return new ResponseEntity(PromocaoDTO.create(promocao), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody PromocaoDTO dto){
        if(!service.getPromocaoById(id).isPresent()){
            return new ResponseEntity("Promoção não encontrada", HttpStatus.NOT_FOUND);
        }
        Promocao promocao = converter(dto);
        promocao.setId(id);
        promocao = service.salvar(promocao);
        return ResponseEntity.ok(PromocaoDTO.create(promocao));
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Promocao> promocao = service.getPromocaoById(id);
        if (!promocao.isPresent()) {
            return new ResponseEntity("Promocao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(promocao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Promocao converter(PromocaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Promocao promocao = modelMapper.map(dto, Promocao.class);
        promocao.setProdutos(references.buscarProdutos(dto.getIdsProdutos()));
        promocao.setServicos(references.buscarServicos(dto.getIdsServicos()));
        return promocao;
    }
}
