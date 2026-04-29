package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.RegistroPrecoProdutoDTO;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/registroPrecoProduto")
@RequiredArgsConstructor
@CrossOrigin
public class RegistroPrecoProdutoController{
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
        return ResponseEntity.ok(registroPrecoProduto.map(RegistroPrecoProdutoDTO::create);
    }
}
