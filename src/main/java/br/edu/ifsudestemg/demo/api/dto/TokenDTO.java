package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String matricula;
    private String token;
    private Cargo cargo;

    public TokenDTO(String matricula, String token) {
        this.matricula = matricula;
        this.token = token;
    }
}
