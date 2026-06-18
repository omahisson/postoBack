package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CredenciaisDTO;
import br.edu.ifsudestemg.demo.api.dto.TokenDTO;
import br.edu.ifsudestemg.demo.api.dto.UsuarioDTO;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.demo.model.entity.Usuario;
import br.edu.ifsudestemg.demo.security.JwtService;
import br.edu.ifsudestemg.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@CrossOrigin
public class UsarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> get() {
        List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (usuario.isEmpty()) {
            return new ResponseEntity<>("Usuario nao encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UsuarioDTO dto) {
        try {
            validarSenhas(dto);
            Usuario usuario = converter(dto);
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            usuario = service.salvar(usuario);
            return new ResponseEntity<>(UsuarioDTO.create(usuario), HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha())
                    .build();
            service.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") Long id, @RequestBody UsuarioDTO dto) {
        if (service.getUsuarioById(id).isEmpty()) {
            return new ResponseEntity<>("Usuario nao encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            validarSenhas(dto);
            Usuario usuario = converter(dto);
            usuario.setId(id);
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            Usuario salvo = service.salvar(usuario);
            return ResponseEntity.ok(UsuarioDTO.create(salvo));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Usuario> usuario = service.getUsuarioById(id);
        if (usuario.isEmpty()) {
            return new ResponseEntity<>("Usuario nao encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(usuario.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }

    private void validarSenhas(UsuarioDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()
                || dto.getSenhaRepeticao() == null || dto.getSenhaRepeticao().trim().isEmpty()) {
            throw new RegraNegocioException("Senha invalida");
        }
        if (!dto.getSenha().equals(dto.getSenhaRepeticao())) {
            throw new RegraNegocioException("Senhas nao conferem");
        }
    }
}
