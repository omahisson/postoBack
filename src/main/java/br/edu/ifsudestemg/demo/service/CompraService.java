package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Compra;
import br.edu.ifsudestemg.demo.model.repository.CompraJpaRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CompraService {
    private CompraJpaRepository repository;

    public CompraService(CompraJpaRepository repository){
        this.repository = repository;
    }

    public List<Compra> getCompra(){
        return repository.findAll();
    }

    public Optional<Compra> getCompraById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Compra salvar(Compra compra) {
        validar(compra);
        return repository.save(compra);
    }

    @Transactional
    public void excluir(Compra compra) {
        Objects.requireNonNull(compra.getId());
        repository.delete(compra);
    }

    public void validar(Compra compra) {
        if(compra.getDataCompra() == null || compra.getDataCompra().isBefore(LocalDate.now())){
            throw new RegraNegocioException("Data inválida");
        }
        if(compra.getValorTotal() == null || compra.getValorTotal().doubleValue() < 0){
            throw new RegraNegocioException("Valor total inválido");
        }
        if (compra.getNumeroNotaFiscal() == null || compra.getNumeroNotaFiscal().trim().isEmpty()) {
            throw new RegraNegocioException("Nota Fiscal inválida.");
        }
        if (compra.getFormaPagamento() == null){
            throw new RegraNegocioException("Forma de pagamento inválida");
        }
        if (compra.getPosto() == null){
            throw new RegraNegocioException("Posto inválido");
        }
        if (compra.getStatus() == null){
            throw new RegraNegocioException("Status inválido");
        }
    }
}
