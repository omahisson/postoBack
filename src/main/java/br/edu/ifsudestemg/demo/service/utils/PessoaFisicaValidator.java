package br.edu.ifsudestemg.demo.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public class PessoaFisicaValidator {
    public static void validar(String cpf, LocalDate nascimento, String rg){
        if(cpf == null || cpf.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF não deve ser vazio.");
        }
        if (!cpfCalculator(cpf)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve conter um CPF válido.");
        }
        if(nascimento == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nascimento não deve ser vazio.");
        }
        if(nascimento.isBefore(LocalDate.of(1800,1,1))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve ser uma data de nascimento válida.");
        }
        if(nascimento.isAfter(LocalDate.now().minusYears(18))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve ser maior de 18 anos.");
        }
        if(rg == null || rg.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RG não deve ser vazio.");
        }
    }

    private static boolean cpfCalculator(String cpf){
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // CPF deve ter 11 dígitos e não ser uma sequência de números iguais
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Cálculo do 1º Dígito Verificador
            int sm = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                int num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            int r = 11 - (sm % 11);
            char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Cálculo do 2º Dígito Verificador
            sm = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                int num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = 11 - (sm % 11);
            char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Verifica se os dígitos calculados coincidem com os do CPF
            return (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}
