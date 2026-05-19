package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ClienteDTO;
import br.edu.ifsudestemg.demo.model.entity.Cliente;
import br.edu.ifsudestemg.demo.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
@CrossOrigin
public class ClienteController {
    private final ClienteService service;

    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> get(){
        List<Cliente> clientes = service.getClientes();
        return ResponseEntity.ok(clientes.stream().map(ClienteDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteDTO>> get(@PathVariable Long id){
        Optional<Cliente> cliente = service.getCliente(id);
        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(cliente.map(ClienteDTO::create));
    }
    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO payload){
        Cliente entity = converter(payload);
        ClienteDTO dto = ClienteDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO payload){
        if (service.getCliente(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Cliente cliente = converter(payload);
        cliente.setId(id);
        ClienteDTO dto = ClienteDTO.create(service.salvar(cliente));
        return ResponseEntity.ok(dto);
    }

    public Cliente converter(ClienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Cliente.class);
    }
}
