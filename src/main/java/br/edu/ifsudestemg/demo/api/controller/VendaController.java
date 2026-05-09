package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.VendaDTO;
import br.edu.ifsudestemg.demo.model.entity.Venda;
import br.edu.ifsudestemg.demo.service.VendaService;
import lombok.RequiredArgsConstructor;
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
            return new ResponseEntity("Combustivel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(venda.map(VendaDTO::create));
    }
}
