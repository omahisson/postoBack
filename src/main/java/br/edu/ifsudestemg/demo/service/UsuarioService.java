package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.SenhaInvalidaException;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.repository.FuncionarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final FuncionarioJpaRepository repository;

    public UserDetails autenticar(String matricula, String senha) {
        UserDetails user = loadUserByUsername(matricula);
        boolean senhasBatem = encoder.matches(senha, user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = repository.findByMaticula(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionario nao encontrado"));

        return User
                .builder()
                .username(funcionario.getMaticula())
                .password(funcionario.getSenha())
                .roles(funcionario.getCargo().name())
                .build();
    }
}
