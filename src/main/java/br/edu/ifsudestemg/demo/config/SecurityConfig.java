package br.edu.ifsudestemg.demo.config;

import br.edu.ifsudestemg.demo.security.JwtAuthFilter;
import br.edu.ifsudestemg.demo.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/error",
                        "/api/v1/usuarios",
                        "/api/v1/usuarios/auth")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UsuarioService usuarioService,
            JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(usuarioService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/pdv/**")
                        .hasAnyRole(ADMIN, USER)

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/produto/**",
                                "/api/v1/combustivel/**",
                                "/api/v1/servico/**",
                                "/api/v1/promocao/**")
                        .hasAnyRole(ADMIN, USER)

                        .requestMatchers(
                                "/api/v1/dashboard/**",
                                "/api/v1/abastecimento/**",
                                "/api/v1/bomba/**",
                                "/api/v1/cliente/**",
                                "/api/v1/compra/**",
                                "/api/v1/fornecedor/**",
                                "/api/v1/historico-combustivel/**",
                                "/api/v1/produto/**",
                                "/api/v1/combustivel/**",
                                "/api/v1/promocao/**",
                                "/api/v1/registroPrecoProduto/**",
                                "/api/v1/registroPrecoServico/**",
                                "/api/v1/servico/**",
                                "/api/v1/turno/**",
                                "/api/v1/venda/**")
                        .hasAnyRole(ADMIN, USER)

                        .requestMatchers("/api/v1/usuarios/**", "/api/v1/funcionario/**", "/api/v1/posto/**")
                        .hasRole(ADMIN)

                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
