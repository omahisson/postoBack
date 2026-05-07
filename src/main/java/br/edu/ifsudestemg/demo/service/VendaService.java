package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.model.entity.Venda;
import br.edu.ifsudestemg.demo.model.repository.VendaJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaJpaRepository repository;

    public List<Venda> getVendas(){
        return repository.findAll();
    }

    public Optional<Venda> getVenda(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Venda salvar(Venda venda) {
        venda.setDataHora(LocalDateTime.now());
        validar(venda);
        venda.setValorLiquido(venda.getValorBruto().subtract(venda.getValorDesconto()));
        return repository.save(venda);
    }

    @Transactional
    public void excluir(Venda venda) {
        Objects.requireNonNull(venda.getId());
        repository.delete(venda);
    }

    public void validar(Venda venda) {
        if (venda.getValorBruto() == null || venda.getValorBruto().compareTo(new BigDecimal(0))<= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor Bruto deve ser um valor válido maior que zero.");
        }
        if (venda.getValorDesconto() == null || venda.getValorBruto().compareTo(new BigDecimal(0))< 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor Bruto deve ser um valor válido maior ou igual a zero.");
        }
        if(venda.getFormaPagamento() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forma de pagamento inválida.");
        }
        if (venda.getPosto() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Posto inválido.");
        }
        if (venda.getStatus() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido.");
        }
    }

}
