package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.RegistroPrecoProdutoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.edu.ifsudestemg.demo.service.RegistroPrecoProdutoService;

@RestController
@RequestMapping("/api/v1/registroPrecoProduto")
@RequiredArgsConstructor
@CrossOrigin
public class RegistroPrecoProdutoController{

    private final RegistroPrecoProdutoService service;
    private final EntityReferenceResolver references;

    @GetMapping()
    public ResponseEntity get(){
        List<RegistroPrecoProduto> registroPrecoProdutos = service.getRegistroPrecoProduto();
        return ResponseEntity.ok(registroPrecoProdutos.stream().map(RegistroPrecoProdutoDTO ::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<RegistroPrecoProduto> registroPrecoProduto = service.getRegistroPrecoProdutoById(id);
        if(!registroPrecoProduto.isPresent()){
            return new ResponseEntity("Registro de preço de produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(registroPrecoProduto.map(RegistroPrecoProdutoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody RegistroPrecoProdutoDTO dto){
        RegistroPrecoProduto registroPrecoProduto = converter(dto);
        registroPrecoProduto.setId(null);
        registroPrecoProduto = service.salvar(registroPrecoProduto);
        return new ResponseEntity(RegistroPrecoProdutoDTO.create(registroPrecoProduto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id ,@RequestBody RegistroPrecoProdutoDTO dto){
        if(!service.getRegistroPrecoProdutoById(id).isPresent()){
            return new ResponseEntity("Registro de preço de produto não encontrado", HttpStatus.NOT_FOUND);
        }
        RegistroPrecoProduto registroPrecoProduto = converter(dto);
        registroPrecoProduto.setId(id);
        registroPrecoProduto = service.salvar(registroPrecoProduto);
        return ResponseEntity.ok(RegistroPrecoProdutoDTO.create(registroPrecoProduto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<RegistroPrecoProduto> registroPrecoProduto = service.getRegistroPrecoProdutoById(id);
        if (!registroPrecoProduto.isPresent()) {
            return new ResponseEntity("Registro de preco de produto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(registroPrecoProduto.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public RegistroPrecoProduto converter(RegistroPrecoProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        RegistroPrecoProduto registroPrecoProduto = modelMapper.map(dto, RegistroPrecoProduto.class);
        registroPrecoProduto.setProduto(references.buscarProduto(dto.getIdProduto()));
        return registroPrecoProduto;
    }
}
