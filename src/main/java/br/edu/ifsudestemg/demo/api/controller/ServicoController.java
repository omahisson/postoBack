package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.ServicoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
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
    private final EntityReferenceResolver references;

    @GetMapping()
    public ResponseEntity<List<ServicoDTO>> get(){
        List<Servico> servicos = service.getServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ServicoDTO>> get(@PathVariable Long id){
        Optional<Servico> servico = service.getServico(id);
        if(servico.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@RequestBody ServicoDTO payload){
        Servico entity = converter(payload);
        entity.setId(null);
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
    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Servico> servico = service.getServico(id);
        if (servico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servico não encontrada");
        }
        try {
            service.excluir(servico.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Servico converter(ServicoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Servico servico = modelMapper.map(dto, Servico.class);
        servico.setPosto(references.buscarPosto(dto.getIdPosto()));
        return servico;
    }
}
