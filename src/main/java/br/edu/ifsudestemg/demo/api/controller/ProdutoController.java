package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ProdutoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Produto;
import br.edu.ifsudestemg.demo.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final EntityReferenceResolver references;

    @GetMapping()
    public ResponseEntity listar(@RequestParam(value = "idPosto", required = false) Long idPosto){
        List<Produto> produtos = idPosto == null ? service.getProduto() : service.getProdutoByPosto(idPosto);
        return ResponseEntity.ok(produtos.stream().map(ProdutoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable("id") Long id){
        Optional<Produto> produto = service.getProdutoById(id);
        if(!produto.isPresent()){
            return new ResponseEntity("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(produto.map(ProdutoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ProdutoDTO dto) {
        Produto produto = converter(dto);
        produto.setId(null);
        produto = service.salvar(produto);
        return new ResponseEntity(ProdutoDTO.create(produto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
        if (!service.getProdutoById(id).isPresent()) {
            return new ResponseEntity("Produto não encontrada", HttpStatus.NOT_FOUND);
        }
        Produto produto = converter(dto);
        produto.setId(id);
        produto = service.salvar(produto);
        return ResponseEntity.ok(ProdutoDTO.create(produto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        Optional<Produto> produto = service.getProdutoById(id);
        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        try {
            service.excluir(produto.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);
        produto.setPosto(references.buscarPosto(dto.getIdPosto()));
        return produto;
    }
}
