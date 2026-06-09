package br.edu.ifsudestemg.demo.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EnderecoValidator {
    public static void validar(String telefone, String email, String logradouro, String numero, String bairro, String cidade, String estado, String cep) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone nao deve ser vazio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email nao deve ser vazio.");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve conter um email valido.");
        }
        if (logradouro == null || logradouro.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logradouro nao deve ser vazio.");
        }
        if (numero == null || numero.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Numero nao deve ser vazio.");
        }
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bairro nao deve ser vazio.");
        }
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade nao deve ser vazio.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado nao deve ser vazio.");
        }
        if (cep == null || cep.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP nao deve ser vazio.");
        }
    }
}
