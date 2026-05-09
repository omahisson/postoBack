package br.edu.ifsudestemg.demo.exception;

import br.edu.ifsudestemg.demo.api.dto.ErroPadronizadoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarErrosDeValidacao(MethodArgumentNotValidException ex) {

        List<FieldError> errosDeCampo = ex.getBindingResult().getFieldErrors();

        String mensagensDeErro = errosDeCampo.stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErroPadronizadoDTO erro = new ErroPadronizadoDTO(
                "BAD_REQUEST_VALIDATION",
                mensagensDeErro,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
