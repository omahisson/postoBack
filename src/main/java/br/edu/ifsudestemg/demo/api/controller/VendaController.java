package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.VendaDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Venda;
import br.edu.ifsudestemg.demo.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/venda")
@RequiredArgsConstructor
@CrossOrigin
public class VendaController {
    private final VendaService service;

    @GetMapping()
    public ResponseEntity<List<VendaDTO>> get(){
        List<Venda> vendas = service.getVendas();
        return ResponseEntity.ok(vendas.stream().map(VendaDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<VendaDTO>> get(@PathVariable Long id){
        Optional<Venda> venda = service.getVenda(id);
        if(venda.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }
    @PostMapping
    public ResponseEntity<VendaDTO> criar(@RequestBody VendaDTO payload){
        Venda entity = converter(payload);
        VendaDTO dto = VendaDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> atualizar(@PathVariable Long id, @RequestBody VendaDTO payload){
        if (service.getVenda(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Venda venda = converter(payload);
        venda.setId(id);
        VendaDTO dto = VendaDTO.create(service.salvar(venda));
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Venda> venda = service.getVenda(id);
        if (venda.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venda não encontrada");
        }
        try {
            service.excluir(venda.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Venda.class);
    }
}
