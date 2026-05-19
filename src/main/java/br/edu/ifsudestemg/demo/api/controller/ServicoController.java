package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ServicoDTO;
import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servico")
@RequiredArgsConstructor
@CrossOrigin
public class ServicoController {
    private final ServicoService service;

    @GetMapping()
    public ResponseEntity<List<ServicoDTO>> get(){
        List<Servico> servicos = service.getServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ServicoDTO>> get(@PathVariable Long id){
        Optional<Servico> servico = service.getServico(id);
        if(servico.isEmpty()){
            return new ResponseEntity("Combustivel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@RequestBody ServicoDTO payload){
        Servico entity = converter(payload);
        ServicoDTO dto = ServicoDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id, @RequestBody ServicoDTO payload){
        if (service.getServico(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Servico servico = converter(payload);
        servico.setId(id);
        ServicoDTO dto = ServicoDTO.create(service.salvar(servico));
        return ResponseEntity.ok(dto);
    }
    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Servico.class);
    }
}
