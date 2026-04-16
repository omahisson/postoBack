package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.repository.PromocaoJpaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PromocaoService {
    private PromocaoJpaRepository repository;

    public PromocaoService(PromocaoJpaRepository repository) {
        this.repository = repository;
    }

    public List<Promocao> getPromocao(){
        return repository.findAll();
    }

    public Optional<Promocao> getPromocaoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Promocao salvar(Promocao promocao) {
        validar(promocao);
        return repository.save(promocao);
    }

    @Transactional
    public void excluir(Promocao promocao) {
        Objects.requireNonNull(promocao.getId());
        repository.delete(promocao);
    }

    public void validar(Promocao promocao) {
        if(promocao.getTitulo() == null || promocao.getTitulo().trim().isEmpty()){
            throw new RegraNegocioException("Título inválido");
        }
        if(promocao.getDescricao() == null || promocao.getDescricao().trim().isEmpty()){
            throw new RegraNegocioException("Descrição inválida");
        }
        if(promocao.getTipoDesconto() == null){
            throw new RegraNegocioException("Tipo de desconto inválido");
        }
        if(promocao.getValorDesconto() == null || promocao.getValorDesconto().doubleValue() < 0){
            throw new RegraNegocioException("Valor do desconto inválido");
        }
        if(promocao.getDataInicio() == null){
            throw new RegraNegocioException("Data de início inválida");
        }
        if(promocao.getDataFim() == null){
            throw new RegraNegocioException("Data de fim inválida");
        }
        if(promocao.getProdutos() == null){
            throw new RegraNegocioException("Produto inválido");
        }
        if(promocao.getServicos() == null){
            throw new RegraNegocioException("Serviço inválido");
        }
        if(promocao.getAtivo() == null){
            throw new RegraNegocioException("Status inválido");
        }
    }
}
