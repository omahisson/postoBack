package br.edu.ifsudestemg.demo.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PessoaJuridicaValidator {
    public static void validar(String cnpj, String razaoSocial, String nomeFantasia, String inscricaoEstadual){
        if(cnpj == null || cnpj.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ não deve ser vazio.");
        }
        if(!isValidCNPJ(cnpj)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ não é válido.");
        }
        if(razaoSocial == null || razaoSocial.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Razão Social não deve ser vazio.");
        }
        if(nomeFantasia == null || nomeFantasia.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome Fantasia não deve ser vazio.");
        }
        if(inscricaoEstadual == null || inscricaoEstadual.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inscrição Estadual não deve ser vazio.");
        }
    }

    private static boolean isValidCNPJ(String cnpj){
        if (cnpj == null) return false;

        // Remove pontuação e garante letras maiúsculas
        String cleanCnpj = cnpj.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        // Valida tamanho e se os últimos 2 dígitos são numéricos
        if (cleanCnpj.length() != 14 || !cleanCnpj.substring(12).matches("[0-9]{2}")) {
            return false;
        }

        // Cálculo do 1º Dígito
        int dv1 = calculateModulo11(cleanCnpj.substring(0, 12), new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        // Cálculo do 2º Dígito (Base + DV1)
        int dv2 = calculateModulo11(cleanCnpj.substring(0, 12) + dv1, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});

        return cleanCnpj.endsWith("" + dv1 + dv2);
    }

    private static int calculateModulo11(String input, int[] weights){
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            // Conversão Alfanumérica 2026: ASCII - 48
            sum += (input.charAt(i) - 48) * weights[i];
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
