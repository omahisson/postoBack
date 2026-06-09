package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.infrastructuries.enums.FormaPagamento;
import br.edu.ifsudestemg.demo.infrastructuries.enums.Status;
import br.edu.ifsudestemg.demo.model.entity.Compra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CompraDTO {
    private Long id;
    private LocalDate dataCompra;
    private BigDecimal valorTotal;
    private String numeroNotaFiscal;
    private FormaPagamento formaPagamento;
    private Long idPosto;
    private Status status;

    public static CompraDTO create(Compra body){
        ModelMapper modelMapper = new ModelMapper();
        CompraDTO dto = modelMapper.map(body, CompraDTO.class);
        if (body.getPosto() != null) {
            dto.setIdPosto(body.getPosto().getId());
        }
        return dto;
    }
}
