package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.TurnoDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Turno;
import br.edu.ifsudestemg.demo.service.TurnoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/turno")
@RequiredArgsConstructor
@CrossOrigin
public class TurnoController {
    private final TurnoService service;

    @GetMapping()
    public ResponseEntity<List<TurnoDTO>> get(){
        List<Turno> turnos = service.getTurnos();
        return ResponseEntity.ok(turnos.stream().map(TurnoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<TurnoDTO>> get(@PathVariable Long id){
        Optional<Turno> turno = service.getTurno(id);
        if(turno.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(turno.map(TurnoDTO::create));
    }
    @PostMapping
    public ResponseEntity<TurnoDTO> criar(@RequestBody TurnoDTO payload){
        Turno entity = converter(payload);
        TurnoDTO dto = TurnoDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> atualizar(@PathVariable Long id, @RequestBody TurnoDTO payload){
        if (service.getTurno(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Turno turno = converter(payload);
        turno.setId(id);
        TurnoDTO dto = TurnoDTO.create(service.salvar(turno));
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Turno> turno = service.getTurno(id);
        if (turno.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno não encontrada");
        }
        try {
            service.excluir(turno.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Turno converter(TurnoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Turno.class);
    }
}
