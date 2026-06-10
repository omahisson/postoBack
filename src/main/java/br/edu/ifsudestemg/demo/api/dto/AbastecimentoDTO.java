package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.Abastecimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimentoDTO {
    private Long id;
    private Long idPosto;
    private Long idCombustivel;
    private String tipoCombustivel;
    private String fornecedor;
    private BigDecimal quantidade;
    private String unidade;
    private String numeroNota;
    private LocalDate dataEntrega;
    private LocalDate dataValidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;

    public static AbastecimentoDTO create(Abastecimento body) {
        AbastecimentoDTO dto = new AbastecimentoDTO();
        dto.setId(body.getId());
        dto.setTipoCombustivel(body.getTipoCombustivel());
        dto.setFornecedor(body.getFornecedor());
        dto.setQuantidade(body.getQuantidade());
        dto.setUnidade(body.getUnidade());
        dto.setNumeroNota(body.getNumeroNota());
        dto.setDataEntrega(body.getDataEntrega());
        dto.setDataValidade(body.getDataValidade());
        dto.setPrecoUnitario(body.getPrecoUnitario());
        dto.setValorTotal(body.getValorTotal());
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        if (body.getCombustivel() != null) {
            dto.setIdCombustivel(body.getCombustivel().getId());
        }
        return dto;
    }
}
