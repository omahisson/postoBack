package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.FormaPagamento;
import br.edu.ifsudestemg.demo.infrastructuries.enums.Status;
import br.edu.ifsudestemg.demo.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private LocalDateTime dataHora;
    private BigDecimal valorBruto;
    private BigDecimal valorDesconto;
    private BigDecimal valorLiquido;
    private FormaPagamento formaPagamento;
    private Long idPosto;
    private Status status;

    public static VendaDTO create(Venda body){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(body, VendaDTO.class);
    }
}
