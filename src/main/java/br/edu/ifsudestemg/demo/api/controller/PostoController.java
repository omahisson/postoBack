package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.PostoDTO;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.service.BombaService;
import br.edu.ifsudestemg.demo.service.PostoService;
import lombok.RequiredArgsConstructor;
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
}
