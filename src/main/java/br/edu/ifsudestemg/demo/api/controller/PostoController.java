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
    private final EntityReferenceResolver references;

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
            posto.setId(null);
            posto = service.salvar(posto);
            return new ResponseEntity(PostoDTO.create(posto), HttpStatus.CREATED);
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
            posto = service.salvar(posto);
            return ResponseEntity.ok(PostoDTO.create(posto));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable("id") Long id) {
        Optional<Posto> posto = service.getPostoById(id);
        if (posto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Posto não encontrado");
        }
        try {
            service.excluir(posto.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Posto converter(PostoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Posto posto = modelMapper.map(dto, Posto.class);
        posto.setPosto(references.buscarPosto(dto.getIdPosto()));
        return posto;
    }
}
