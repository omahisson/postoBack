package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ClienteDTO;
import br.edu.ifsudestemg.demo.api.dto.FornecedorDTO;
import br.edu.ifsudestemg.demo.model.entity.Cliente;
import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import br.edu.ifsudestemg.demo.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fornecedor")
@RequiredArgsConstructor
@CrossOrigin
public class FornecedorController {
    private final FornecedorService service;

    @GetMapping()
    public ResponseEntity<List<FornecedorDTO>> get(){
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FornecedorDTO>> get(@PathVariable Long id){
        Optional<Fornecedor> fornecedor = service.getFornecedor(id);
        if(fornecedor.isEmpty()){
            return new ResponseEntity("Combustivel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }
}
