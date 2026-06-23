package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.FuncionarioDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import br.edu.ifsudestemg.demo.model.entity.Administrador;
import br.edu.ifsudestemg.demo.model.entity.Colaborador;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.entity.Gerente;
import br.edu.ifsudestemg.demo.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionario")
@RequiredArgsConstructor
@CrossOrigin
public class FuncionarioController {
    private final FuncionarioService service;
    private final EntityReferenceResolver references;

    @GetMapping()
    public ResponseEntity<List<FuncionarioDTO>> get(){
        List<Funcionario> funcionarios = service.getFuncionarios();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<FuncionarioDTO>> get(@PathVariable Long id){
        Optional<Funcionario> funcionario = service.getFuncionario(id);
        if(funcionario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }
    @PostMapping
    public ResponseEntity<FuncionarioDTO> criar(@RequestBody FuncionarioDTO payload, Authentication authentication){
        boolean gerente = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_GERENTE"));
        if (gerente && payload.getCargo() != Cargo.COLABORADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Funcionario entity = converter(payload);
        entity.setId(null);
        FuncionarioDTO dto = FuncionarioDTO.create(service.salvar(entity));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long id, @RequestBody FuncionarioDTO payload){
        if (service.getFuncionario(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
        }
        Funcionario funcionario = converter(payload);
        funcionario.setId(id);
        FuncionarioDTO dto = FuncionarioDTO.create(service.salvar(funcionario));
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        Optional<Funcionario> funcionario = service.getFuncionario(id);
        if (funcionario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrada");
        }
        try {
            service.excluir(funcionario.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = funcionarioPorCargo(dto.getCargo());
        modelMapper.map(dto, funcionario);
        funcionario.setPosto(references.buscarPosto(dto.getIdPosto()));
        if (funcionario instanceof Gerente gerente) {
            gerente.setPostosVinculados(new LinkedHashSet<>(dto.getPostosVinculados() == null
                    ? List.of(dto.getIdPosto())
                    : dto.getPostosVinculados()));
        }
        return funcionario;
    }

    private Funcionario funcionarioPorCargo(Cargo cargo) {
        if (cargo == null) {
            throw new RegraNegocioException("Cargo deve ser informado.");
        }
        return switch (cargo) {
            case ADMINISTRADOR -> new Administrador();
            case GERENTE -> new Gerente();
            case COLABORADOR -> new Colaborador();
        };
    }
}
