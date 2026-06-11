package br.edu.ifsudestemg.demo.api.dto;

import br.edu.ifsudestemg.demo.model.entity.PdvTurno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdvTurnoDTO {
    private Long id;
    private Long idPosto;
    private Long operadorId;
    private String operadorNome;
    private String turno;
    private LocalDateTime horaAberturaISO;
    private LocalDateTime horaFechamentoISO;
    private BigDecimal valorInicialCaixa;
    private BigDecimal valorFinalCaixa;
    private BigDecimal valorEsperado;
    private BigDecimal diferenca;
    private BigDecimal totalVendas;
    private BigDecimal totalCartao;
    private BigDecimal totalDinheiro;
    private Integer totalTransacoes;
    private String status;

    public static PdvTurnoDTO create(PdvTurno turno) {
        PdvTurnoDTO dto = new PdvTurnoDTO();
        dto.setId(turno.getId());
        dto.setIdPosto(turno.getPosto() == null ? null : turno.getPosto().getId());
        dto.setOperadorId(turno.getOperador() == null ? null : turno.getOperador().getId());
        dto.setOperadorNome(turno.getOperadorNome());
        dto.setTurno(turno.getTurno());
        dto.setHoraAberturaISO(turno.getHoraAbertura());
        dto.setHoraFechamentoISO(turno.getHoraFechamento());
        dto.setValorInicialCaixa(turno.getValorInicialCaixa());
        dto.setValorFinalCaixa(turno.getValorFinalCaixa());
        dto.setValorEsperado(turno.getValorEsperado());
        dto.setDiferenca(turno.getDiferenca());
        dto.setTotalVendas(turno.getTotalVendas());
        dto.setTotalCartao(turno.getTotalCartao());
        dto.setTotalDinheiro(turno.getTotalDinheiro());
        dto.setTotalTransacoes(turno.getTotalTransacoes());
        dto.setStatus(turno.getStatus());
        return dto;
    }
}
