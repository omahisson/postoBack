package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.api.dto.BombaRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.BombaResponseDTO;
import br.edu.ifsudestemg.demo.api.mapper.BombaMapper;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import br.edu.ifsudestemg.demo.model.repository.BombaJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BombaService {
    private final BombaJpaRepository repository;
    private final BombaMapper mapper;

    public List<BombaResponseDTO> getBombas() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    public BombaResponseDTO getBomba(Long id) {
        return mapper.toResponseDTO(getBombaById(id));
    }

    @Transactional
    public BombaResponseDTO create(BombaRequestDTO request){
        if(repository.existsByNumeroSerie(request.numeroSerie())){
            throw new RegraNegocioException("Número de série já cadastrado.");
        }
        Bomba bomba = mapper.toEntity(request);
        bomba.setAtivo(true);
        return mapper.toResponseDTO(repository.save(bomba));
    }

    @Transactional
    public BombaResponseDTO update(Long id, BombaRequestDTO request){
        Bomba bomba = getBombaById(id);
        mapper.updateBombaFromDTO(request, bomba);
        return mapper.toResponseDTO(repository.save(bomba));
    }

    @Transactional
    public void delete(Long id){
        Bomba bomba = getBombaById(id);
        repository.delete(bomba);
    }

    private Bomba getBombaById(Long id){
        return repository.findById(id).orElseThrow(()-> new RegraNegocioException("Bomba não encontrada."));
    }
}