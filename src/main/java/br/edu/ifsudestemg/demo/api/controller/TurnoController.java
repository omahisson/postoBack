package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.TurnoDTO;
import br.edu.ifsudestemg.demo.model.entity.Turno;
import br.edu.ifsudestemg.demo.service.TurnoService;
import lombok.RequiredArgsConstructor;
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
            return new ResponseEntity("Combustivel não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(turno.map(TurnoDTO::create));
    }
}
