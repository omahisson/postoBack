package br.edu.ifsudestemg.demo.api.mapper;

import br.edu.ifsudestemg.demo.api.dto.ClienteRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.ClienteResponseDTO;
import br.edu.ifsudestemg.demo.model.entity.Cliente;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteResponseDTO toResponseDTO(Cliente entity);

    Cliente toEntity(ClienteRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClienteFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente entity);
}
