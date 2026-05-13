package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.RegistroPrecoServicoDTO;
import br.edu.ifsudestemg.demo.service.RegistroPrecoServicoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;


@RestController
@RequestMapping("/api/v1/registroPrecoServico")
@RequiredArgsConstructor
@CrossOrigin
public class RegistroPrecoServicoController {
    private final RegistroPrecoServicoService service;

    @GetMapping
    public ResponseEntity get(){
        List<RegistroPrecoServico> registroPrecoServicos = service.getRegistroPrecoServico();
        return ResponseEntity.ok(registroPrecoServicos.stream().map(RegistroPrecoServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<RegistroPrecoServico> registroPrecoServico = service.getRegistroPrecoServicoById(id);
        if(!registroPrecoServico.isPresent()){
            return new ResponseEntity("Registro de preço de serviço não encontrado",HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(registroPrecoServico.map(RegistroPrecoServicoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody RegistroPrecoServicoDTO dto){
        RegistroPrecoServico registroPrecoServico = converter(dto);
        registroPrecoServico = service.salvar(registroPrecoServico);
        return new ResponseEntity(registroPrecoServico, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity put(@PathVariable("id") Long id ,@RequestBody RegistroPrecoServicoDTO dto){
        if(!service.getRegistroPrecoServicoById(id).isPresent()){
            return new ResponseEntity("Registro de preço de serviço não encontrado",HttpStatus.NOT_FOUND);
        }
        RegistroPrecoServico registroPrecoServico = converter(dto);
        registroPrecoServico.setId(id);
        service.salvar(registroPrecoServico);
        return ResponseEntity.ok(registroPrecoServico);
    }

    public RegistroPrecoServico converter(RegistroPrecoServicoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        RegistroPrecoServico registroPrecoServico = modelMapper.map(dto, RegistroPrecoServico.class);
        return registroPrecoServico;
    }
}
