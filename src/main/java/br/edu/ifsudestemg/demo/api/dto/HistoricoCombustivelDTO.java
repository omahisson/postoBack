package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.HistoricoCombustivel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoCombustivelDTO {
    private Long id;
    private Long idPosto;
    private Long idCombustivel;
    private Long tipoCombustivelId;
    private String tipoCombustivel;
    private String nome;
    private String fornecedor;
    private BigDecimal estoque;
    private BigDecimal precoAnterior;
    private BigDecimal novoPreco;
    private LocalDate dataVigencia;
    private String responsavel;
    private String motivo;
    private LocalDate dataAlteracao;

    public static HistoricoCombustivelDTO create(HistoricoCombustivel body) {
        HistoricoCombustivelDTO dto = new HistoricoCombustivelDTO();
        dto.setId(body.getId());
        dto.setTipoCombustivel(body.getTipoCombustivel());
        dto.setPrecoAnterior(body.getPrecoAnterior());
        dto.setNovoPreco(body.getNovoPreco());
        dto.setDataVigencia(body.getDataVigencia());
        dto.setResponsavel(body.getResponsavel());
        dto.setMotivo(body.getMotivo());
        dto.setDataAlteracao(body.getDataAlteracao());
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        if (body.getCombustivel() != null) {
            dto.setIdCombustivel(body.getCombustivel().getId());
            dto.setTipoCombustivelId(body.getCombustivel().getId());
        }
        return dto;
    }
}
