package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoProduto;
import br.edu.ifsudestemg.demo.model.entity.RegistroPrecoServico;
import br.edu.ifsudestemg.demo.model.repository.RegistroPrecoServicoJpaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RegistroPrecoServicoService{
    private RegistroPrecoServicoJpaRepository repository;

    public RegistroPrecoServicoService(RegistroPrecoServicoJpaRepository repository) {
        this.repository = repository;
    }

    public List<RegistroPrecoServico> getRegistroPrecoServico(){
        return repository.findAll();
    }

    public Optional<RegistroPrecoServico> getRegistroPrecoServicoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public RegistroPrecoServico salvar(RegistroPrecoServico registroPrecoServico) {
        validar(registroPrecoServico);
        return repository.save(registroPrecoServico);
    }

    @Transactional
    public void excluir(RegistroPrecoServico registroPrecoServico) {
        Objects.requireNonNull(registroPrecoServico.getId());
        repository.delete(registroPrecoServico);
    }

    public void validar(RegistroPrecoServico registroPrecoServico) {
        if(registroPrecoServico.getValor() == null || registroPrecoServico.getValor().doubleValue() < 0){
            throw new RegraNegocioException("Valor inválido.");
        }
        if(registroPrecoServico.getDataInicioVigencia() == null){
            throw new RegraNegocioException("Data início inválida.");
        }
        if(registroPrecoServico.getDataFimVigencia() == null){
            throw new RegraNegocioException("Data fim inválida");
        }
        if(registroPrecoServico.getAtivo() == null){
            throw new RegraNegocioException("Status inválido.");
        }
        if (registroPrecoServico.getServico() == null) {
            throw new RegraNegocioException("Produto inválido.");
        }
    }

}
