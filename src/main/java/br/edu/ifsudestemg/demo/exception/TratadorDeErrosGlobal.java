package br.edu.ifsudestemg.demo.exception;

import br.edu.ifsudestemg.demo.api.dto.ErroPadronizadoDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TratadorDeErrosGlobal {
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarRegraDeNegocio(RegraNegocioException ex) {
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                "BAD_REQUEST",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarViolacaoDeIntegridade(DataIntegrityViolationException ex) {
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                "BAD_REQUEST",
                mensagemDeVinculo(ex),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                status.name(),
                ex.getReason(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarIdInvalidoNoPost(ObjectOptimisticLockingFailureException ex) {
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                "BAD_REQUEST",
                "Para criar um registro novo, nao envie o campo id. O banco gera esse valor automaticamente.",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                "BAD_REQUEST",
                mensagemJsonInvalido(ex),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    private String mensagemDeVinculo(DataIntegrityViolationException ex) {
        String detalhe = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        if (detalhe == null) {
            return "Registro nao pode ser alterado ou excluido porque esta vinculado a outro registro.";
        }

        String texto = detalhe.toLowerCase();
        if (texto.contains("promocao_servicos")) {
            return "Servico nao pode ser excluido porque esta vinculado a uma promocao. Remova esse servico da promocao antes de excluir.";
        }
        if (texto.contains("promocao_produtos")) {
            return "Produto nao pode ser excluido porque esta vinculado a uma promocao. Remova esse produto da promocao antes de excluir.";
        }
        if (texto.contains("registro_preco_servico")) {
            return "Servico nao pode ser excluido porque possui historico de preco vinculado. Exclua ou ajuste o historico antes.";
        }
        if (texto.contains("registro_preco_produto")) {
            return "Produto nao pode ser excluido porque possui historico de preco vinculado. Exclua ou ajuste o historico antes.";
        }
        if (texto.contains("posto_id")) {
            return "Registro nao pode ser salvo porque o posto informado nao existe ou esta invalido.";
        }
        if (texto.contains("foreign key") || texto.contains("violates foreign key constraint")) {
            return "Registro nao pode ser excluido porque esta vinculado a outro registro. Remova o vinculo antes de excluir.";
        }

        return "Registro nao pode ser salvo, alterado ou excluido por violar uma regra de integridade do banco.";
    }

    private String mensagemJsonInvalido(HttpMessageNotReadableException ex) {
        String detalhe = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        if (detalhe != null && detalhe.contains("from Array value")) {
            return "Formato do JSON invalido: envie um unico objeto entre chaves { }, nao uma lista entre colchetes [ ].";
        }
        if (detalhe != null && detalhe.contains("Cannot deserialize")) {
            return "Formato do JSON invalido: confira se os campos enviados batem com o modelo esperado pelo endpoint.";
        }

        return "Corpo da requisicao invalido: confira se o JSON esta bem formado.";
    }
}
