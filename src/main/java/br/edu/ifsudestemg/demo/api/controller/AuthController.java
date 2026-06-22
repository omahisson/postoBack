package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CredenciaisDTO;
import br.edu.ifsudestemg.demo.api.dto.TokenDTO;
import br.edu.ifsudestemg.demo.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import br.edu.ifsudestemg.demo.security.JwtService;
import br.edu.ifsudestemg.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UsuarioService usuarioService;
    private final FuncionarioJpaRepository funcionarioRepository;
    private final JwtService jwtService;

    @PostMapping
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {
            usuarioService.autenticar(credenciais.getMatricula(), credenciais.getSenha());
            Funcionario funcionario = funcionarioRepository.findByMaticula(credenciais.getMatricula())
                    .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado"));

            return new TokenDTO(funcionario.getMaticula(), jwtService.gerarToken(funcionario));
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Matricula ou senha invalidas");
        }
    }
}
