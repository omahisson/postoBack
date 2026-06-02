package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ClienteRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.ClienteResponseDTO;
import br.edu.ifsudestemg.demo.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
@CrossOrigin
public class ClienteController {
    private final ClienteService service;

    @GetMapping()
    public ResponseEntity<List<ClienteResponseDTO>> get(){
        List<ClienteResponseDTO> clientes = service.getClientes();
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> get(@PathVariable Long id){
        ClienteResponseDTO cliente = service.getCliente(id);
        return ResponseEntity.ok(cliente);
    }
    @PostMapping()
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteRequestDTO request){
        ClienteResponseDTO cliente = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> update(@PathVariable Long id, @RequestBody ClienteRequestDTO request){
        ClienteResponseDTO cliente = service.update(id, request);
        return ResponseEntity.ok(cliente);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
