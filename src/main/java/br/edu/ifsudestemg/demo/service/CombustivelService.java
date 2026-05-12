package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Combustivel;
import br.edu.ifsudestemg.demo.model.entity.Posto;
import br.edu.ifsudestemg.demo.model.repository.CombustivelJpaRepository;
import br.edu.ifsudestemg.demo.model.repository.PostoJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.edu.ifsudestemg.demo.service.utils.EnderecoValidator.validar;

@Service
public class CombustivelService {
    private CombustivelJpaRepository repository;

    public CombustivelService(CombustivelJpaRepository repository){
        this.repository = repository;
    }

    public List<Combustivel> getCombustivel(){
        return repository.findAll();
    }

    public Optional<Combustivel> getCombustivelById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Combustivel salvar(Combustivel combustivel){
        validar(combustivel);
        return repository.save(combustivel);
    }

    @Transactional
    public void excluir(Combustivel combustivel){
        Objects.requireNonNull(combustivel.getId());;
        repository.delete(combustivel);
    }

    public void validarString(String valor, String mensagem){
        if(valor == null || valor.isBlank()){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validarNString(Object valor, String mensagem){
        if(valor == null){
            throw new RegraNegocioException(mensagem);
        }
    }

    public void validar(Combustivel combustivel){
        validarString(combustivel.getTipoCombustivel(), "Tipo invalido");
        validarString(combustivel.getOctanagem(), "Octanagem invalido");
        validarString(combustivel.getComposicao(), "Composicao invalido");
        validarString(combustivel.getCodigoBarras(), "Codigo barras invalido");
        validarString(combustivel.getMarca(), "Marca invalido");
        validarString(combustivel.getCategoria(), "Categoria invalido");
        validarString(combustivel.getNome(), "Nome invalido");
        validarNString(combustivel.getPreco(), "Preço invalido"); //validar dps negativo
        validarString(combustivel.getDescricao(), "Descricao invalido");
        validarString(combustivel.getUnidade(), "Unidade invalido");
        validarNString(combustivel.getPosto(), "Posto invalido");
        validarNString(combustivel.getAtivo(), "Ativo invalido");
    }
}
