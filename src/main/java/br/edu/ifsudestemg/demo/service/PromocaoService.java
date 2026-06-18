package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Promocao;
import br.edu.ifsudestemg.demo.model.repository.PromocaoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
            throw new RegraNegocioException("Titulo invalido");
        }
        if(promocao.getDescricao() == null || promocao.getDescricao().trim().isEmpty()){
            throw new RegraNegocioException("Descricao invalida");
        }
        if(promocao.getTipoDesconto() == null){
            throw new RegraNegocioException("Tipo de desconto invalido");
        }
        if(promocao.getValorDesconto() == null || promocao.getValorDesconto().signum() < 0){
            throw new RegraNegocioException("Valor do desconto invalido");
        }
        if(promocao.getDataInicio() == null){
            throw new RegraNegocioException("Data de inicio invalida");
        }
        if(promocao.getDataFim() == null){
            throw new RegraNegocioException("Data de fim invalida");
        }
        boolean semProdutos = promocao.getProdutos() == null || promocao.getProdutos().isEmpty();
        boolean semServicos = promocao.getServicos() == null || promocao.getServicos().isEmpty();
        if(semProdutos && semServicos){
            throw new RegraNegocioException("Informe ao menos um produto ou servico para a promocao");
        }
        if(promocao.getAtivo() == null){
            throw new RegraNegocioException("Status invalido");
        }
    }
}
