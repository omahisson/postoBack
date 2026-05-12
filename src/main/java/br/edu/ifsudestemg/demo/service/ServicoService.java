package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Servico;
import br.edu.ifsudestemg.demo.model.repository.ServicoJpaRepository;
import jakarta.transaction.Transactional;
<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> dc3577fbe146afbfe0fc8eaa31576fc55ad9b5af
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
<<<<<<< HEAD
=======
@RequiredArgsConstructor
>>>>>>> dc3577fbe146afbfe0fc8eaa31576fc55ad9b5af
public class ServicoService {
    private ServicoJpaRepository repository;

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServico(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Servico salvar(Servico servico) {
        validar(servico);
        return repository.save(servico);
    }

    @Transactional
    public void excluir(Servico servico) {
        Objects.requireNonNull(servico.getId());
        repository.delete(servico);
    }

    public void validar(Servico servico) {
        if(servico.getNome() == null || servico.getNome().trim().isEmpty()){
            throw new RegraNegocioException("Nome inválido");
        }
        if(servico.getPreco() == null || servico.getPreco().doubleValue() < 0){
            throw new RegraNegocioException("Preço inválido");
        }
        if(servico.getDescricao() == null || servico.getDescricao().trim().isEmpty()){
            throw new RegraNegocioException("Descrição inválida");
        }
        if(servico.getPosto() == null){
            throw new RegraNegocioException("Posto inválido");
        }
        if(servico.getAtivo() == null){
            throw new RegraNegocioException("Status inválido");
        }
        if(servico.getDuracaoEstimadaMinutos() == null || servico.getDuracaoEstimadaMinutos().trim().isEmpty()){
            throw new RegraNegocioException("Duração inválida");
        }
        if (servico.getRequerAgendamento() == null){
            throw new RegraNegocioException("Agendamento inválido");
        }
        if (servico.getDescricaoTecnica() == null || servico.getDescricaoTecnica().trim().isEmpty()){
            throw new RegraNegocioException("Descrição tecnica inválida");
        }
    }
}
