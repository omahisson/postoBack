package br.edu.ifsudestemg.demo.exception;

import br.edu.ifsudestemg.demo.api.dto.ErroPadronizadoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
