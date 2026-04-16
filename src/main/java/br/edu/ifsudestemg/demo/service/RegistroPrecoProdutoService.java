package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.RegistroPreco;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoProdutoJpaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RegistroPrecoProdutoService{
    private RegistroPrecoProdutoJpaRepository repository;

    public RegistroPrecoProdutoService(RegistroPrecoProdutoJpaRepository repository){
        this.repository = repository;
    }

    public List<RegistroPrecoProduto> getRegistroPrecoProduto(){
        return repository.findAll();
    }

    public Optional<RegistroPrecoProduto> getRegistroPrecoProdutoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public RegistroPrecoProduto salvar(RegistroPrecoProduto registroPrecoProduto) {
        validar(registroPrecoProduto);
        return repository.save(registroPrecoProduto);
    }

    @Transactional
    public void excluir(RegistroPrecoProduto registroPrecoProduto) {
        Objects.requireNonNull(registroPrecoProduto.getId());
        repository.delete(registroPrecoProduto);
    }

    public void validar(RegistroPrecoProduto registroPrecoProduto) {
        if(registroPrecoProduto.getValor() == null || registroPrecoProduto.getValor().doubleValue() < 0){
            throw new RegraNegocioException("Valor inválido.");
        }
        if(registroPrecoProduto.getDataInicioVigencia() == null){
            throw new RegraNegocioException("Data início inválida.");
        }
        if(registroPrecoProduto.getDataFimVigencia() == null){
            throw new RegraNegocioException("Data fim inválida");
        }
        if(registroPrecoProduto.getAtivo() == null){
            throw new RegraNegocioException("Status inválido.");
        }
        if (registroPrecoProduto.getProduto() == null) {
            throw new RegraNegocioException("Produto inválido.");
        }
    }

}
