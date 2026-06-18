package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final FuncionarioJpaRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByMaticula(login)
                .or(() -> funcionarioRepository.findByEmail(login))
                .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado: " + login));

        String role = "ROLE_" + funcionario.getCargo().name();

        return new User(
                funcionario.getMaticula(),
                funcionario.getSenha(),
                Boolean.TRUE.equals(funcionario.getAtivo()),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(role)));
    }
}
