package br.edu.ifsudestemg.demo.service.utils;

import br.edu.ifsudestemg.demo.model.entity.Posto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PessoaValidator {
    public static void validar(String nome, Posto posto){
        if(nome == null || nome.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome não deve ser vazio.");
        }
        if(posto == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve conter um posto válido.");
        }
    }
}
