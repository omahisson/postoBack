package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.Cargo;
import br.edu.ifsudestemg.demo.model.entity.Funcionario;
import br.edu.ifsudestemg.demo.model.entity.Gerente;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
    private String maticula;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private String senha;
    private String setor;
    private BigDecimal bonusMeta;
    private Cargo cargo;
    private String cpf;
    private LocalDate dataNascimento;
    private String rg;
    private Long id;
    private String nome;
    private LocalDate dataCadastro;
    private Long idPosto;
    private Boolean ativo;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private List<Long> postosVinculados;

    public static FuncionarioDTO create(Funcionario body){
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(body, FuncionarioDTO.class);
        dto.setSenha(null);
        dto.setCargo(body.getCargo());
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        if (body instanceof Gerente gerente) {
            dto.setPostosVinculados(gerente.getPostosVinculados().stream().toList());
        }
        return dto;
    }
}
