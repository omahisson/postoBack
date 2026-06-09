package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.FornecedorDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Fornecedor;
import br.edu.ifsudestemg.demo.service.FornecedorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final EntityReferenceResolver references;

    @GetMapping()
    public ResponseEntity<List<FornecedorDTO>> get(){
        List<Fornecedor> fornecedores = service.getFornecedores();
        return ResponseEntity.ok(fornecedores.stream().map(FornecedorDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FornecedorDTO>> get(@PathVariable Long id){
        Optional<Fornecedor> fornecedor = service.getFornecedor(id);
        if(fornecedor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(fornecedor.map(FornecedorDTO::create));
    }
    @PostMapping
    public ResponseEntity<FornecedorDTO> criar(@RequestBody FornecedorDTO payload){
        Fornecedor entity = converter(payload);
        entity.setId(null);
        FornecedorDTO dto = FornecedorDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorDTO> atualizar(@PathVariable Long id, @RequestBody FornecedorDTO payload){
        if (service.getFornecedor(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Fornecedor fornecedor = converter(payload);
        fornecedor.setId(id);
        FornecedorDTO dto = FornecedorDTO.create(service.salvar(fornecedor));
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = service.getFornecedor(id);
        if (fornecedor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrada");
        }
        try {
            service.excluir(fornecedor.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);
        fornecedor.setPosto(references.buscarPosto(dto.getIdPosto()));
        return fornecedor;
    }
}
