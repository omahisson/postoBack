package br.edu.ifsudestemg.demo.service;

import br.edu.ifsudestemg.demo.api.dto.ClienteRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.ClienteResponseDTO;
import br.edu.ifsudestemg.demo.api.mapper.ClienteMapper;
import br.edu.ifsudestemg.demo.exception.RegraNegocioException;
import br.edu.ifsudestemg.demo.model.entity.Cliente;
import br.edu.ifsudestemg.demo.model.repository.ClienteJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteJpaRepository repository;
    private final ClienteMapper mapper;

    public List<ClienteResponseDTO> getClientes() {
        return repository.findAll().stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    public ClienteResponseDTO getCliente(Long id) {
        return mapper.toResponseDTO(getClienteById(id));
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO request){
        Cliente cliente = mapper.toEntity(request);
        cliente.setDataCadastro(LocalDate.now());
        cliente.setAtivo(true);
        return mapper.toResponseDTO(repository.save(cliente));
    }
    
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteRequestDTO request){
        Cliente cliente = getClienteById(id);
        mapper.updateClienteFromDTO(request, cliente);
        return mapper.toResponseDTO(repository.save(cliente));
    }

    @Transactional
    public void delete(Long id){
        Cliente cliente = getClienteById(id);
        repository.delete(cliente);
    }

    private Cliente getClienteById(Long id){
        return repository.findById(id).orElseThrow(()-> new RegraNegocioException("Cliente não encontrada."));
    }
}
