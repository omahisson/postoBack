package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ProdutoDTO;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.service.BombaService;
import br.edu.ifsudestemg.demo.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/produto")
@RequiredArgsConstructor
@CrossOrigin
public class ProdutoController {
    private final ProdutoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Produto> produtos = service.getProduto();
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Produto> produto = service.getProdutoById(id);
        if(!produto.isPresent()){
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }
}
