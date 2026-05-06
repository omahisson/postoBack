package br.edu.ifsudestemg.demo.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EnderecoValidator {
    public static void validar(String telefone, String email, String logradouro, String numero, String bairro, String cidade, String estado, String cep){
        if(telefone == null || telefone.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone não deve ser vazio.");
        }
        if(email == null || email.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não deve ser vazio.");
        }
        if(email.contains("@")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve conter um email válido.");
        }
        if(logradouro == null || logradouro.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Logradouro não deve ser vazio.");
        }
        if(numero == null || numero.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Numero não deve ser vazio.");
        }if(bairro == null || bairro.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bairro não deve ser vazio.");
        }
        if(cidade == null || cidade.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não deve ser vazio.");
        }
        if(estado == null || estado.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado não deve ser vazio.");
        }
        if(cep == null || cep.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP não deve ser vazio.");
        }
    }
}
