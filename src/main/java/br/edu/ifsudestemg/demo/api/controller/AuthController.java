package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.api.dto.CredenciaisDTO;
import br.edu.ifsudestemg.demo.api.dto.CadastroAdministradorDTO;
import br.edu.ifsudestemg.demo.api.dto.TokenDTO;
import br.edu.ifsudestemg.demo.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.entity.Administrador;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import br.edu.ifsudestemg.demo.security.JwtService;
import br.edu.ifsudestemg.demo.service.FuncionarioService;
import br.edu.ifsudestemg.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UsuarioService usuarioService;
    private final FuncionarioService funcionarioService;
    private final FuncionarioJpaRepository funcionarioRepository;
    private final JwtService jwtService;

    @PostMapping
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {
            usuarioService.autenticar(credenciais.getMatricula(), credenciais.getSenha());
            Funcionario funcionario = funcionarioRepository.findByMaticula(credenciais.getMatricula())
                    .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado"));

            TokenDTO resposta = new TokenDTO(funcionario.getMaticula(), jwtService.gerarToken(funcionario));
            resposta.setCargo(funcionario.getCargo());
            return resposta;
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Matricula ou senha invalidas");
        }
    }

    @PostMapping("/cadastro")
    public TokenDTO cadastrarAdministrador(@RequestBody CadastroAdministradorDTO cadastro) {
        if (cadastro.getSenha() == null || !cadastro.getSenha().equals(cadastro.getSenhaRepeticao())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As senhas nao conferem.");
        }

        Administrador administrador = new Administrador();
        administrador.setNome(cadastro.getNome());
        administrador.setMaticula(cadastro.getMatricula());
        administrador.setSenha(cadastro.getSenha());
        administrador.setCpf(cadastro.getCpf());
        administrador.setDataNascimento(cadastro.getDataNascimento());
        administrador.setRg(cadastro.getRg());
        administrador.setTelefone(cadastro.getTelefone());
        administrador.setEmail(cadastro.getEmail());
        administrador.setLogradouro(cadastro.getLogradouro());
        administrador.setNumero(cadastro.getNumero());
        administrador.setBairro(cadastro.getBairro());
        administrador.setCidade(cadastro.getCidade());
        administrador.setEstado(cadastro.getEstado());
        administrador.setCep(cadastro.getCep());
        administrador.setSalario(cadastro.getSalario() == null ? BigDecimal.ONE : cadastro.getSalario());
        administrador.setDataAdmissao(LocalDate.now());
        administrador.setSetor("Administracao");

        Funcionario salvo = funcionarioService.salvar(administrador);
        TokenDTO resposta = new TokenDTO(salvo.getMaticula(), jwtService.gerarToken(salvo));
        resposta.setCargo(salvo.getCargo());
        return resposta;
    }

    @GetMapping("/matricula-disponivel")
    public Map<String, Boolean> matriculaDisponivel(@RequestParam String matricula) {
        boolean disponivel = matricula != null
                && !matricula.trim().isEmpty()
                && !funcionarioRepository.existsByMaticula(matricula.trim());
        return Map.of("disponivel", disponivel);
    }
}
