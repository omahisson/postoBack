package br.edu.ifsudestemg.demo.api.controller;

import br.edu.ifsudestemg.demo.model.entity.Promocao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promocao")
@RequiredArgsConstructor
@CrossOrigin
public class PromocaoController {

}
