package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.PdvVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvVendaDTO {
    private Long id;
    private Long idPosto;
    private Long idTurno;
    private List<PdvVendaItemDTO> itens = new ArrayList<>();
    private BigDecimal total;
    private String formaPagamento;
    private LocalDateTime data;
    private Boolean cancelada;
    private LocalDateTime canceladaEm;
    private String motivoCancelamento;

    public static PdvVendaDTO create(PdvVenda venda) {
        PdvVendaDTO dto = new PdvVendaDTO();
        dto.setId(venda.getId());
        dto.setIdPosto(venda.getPosto() == null ? null : venda.getPosto().getId());
        dto.setIdTurno(venda.getTurno() == null ? null : venda.getTurno().getId());
        dto.setItens(venda.getItens() == null ? new ArrayList<>() : venda.getItens().stream().map(PdvVendaItemDTO::create).toList());
        dto.setTotal(venda.getTotal());
        dto.setFormaPagamento(venda.getFormaPagamento());
        dto.setData(venda.getData());
        dto.setCancelada(venda.getCancelada());
        dto.setCanceladaEm(venda.getCanceladaEm());
        dto.setMotivoCancelamento(venda.getMotivoCancelamento());
        return dto;
    }
}
