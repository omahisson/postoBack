package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.PostoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.service.PostoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/posto")
@RequiredArgsConstructor
@CrossOrigin
public class PostoController {
    private final PostoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Posto> postos = service.getPosto();
        return ResponseEntity.ok(postos.stream().map(PostoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Posto> posto = service.getPostoById(id);
        if(!posto.isPresent()){
            return new ResponseEntity("Posto não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(posto.map(PostoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody PostoDTO dto) {
        try {
            Posto posto = converter(dto);
            posto = service.salvar(posto);
            return new ResponseEntity(posto, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PostoDTO dto) {
        if (!service.getPostoById(id).isPresent()) {
            return new ResponseEntity("Posto não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Posto posto = converter(dto);
            posto.setId(id);
            service.salvar(posto);
            return ResponseEntity.ok(posto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Posto converter(PostoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Posto posto = modelMapper.map(dto, Posto.class);
        return posto;
    }
}
