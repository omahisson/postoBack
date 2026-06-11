package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.PdvVendaItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvVendaItemDTO {
    private String tipo;
    private Long itemId;
    private String titulo;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
    private String unidade;

    public static PdvVendaItemDTO create(PdvVendaItem item) {
        return new PdvVendaItemDTO(
                item.getTipo(),
                item.getItemId(),
                item.getTitulo(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getValorTotal(),
                item.getUnidade()
        );
    }

    public PdvVendaItem toEntity() {
        return new PdvVendaItem(tipo, itemId, titulo, quantidade, precoUnitario, valorTotal, unidade);
    }
}
