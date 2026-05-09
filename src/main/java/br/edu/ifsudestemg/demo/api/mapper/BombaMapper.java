package br.edu.ifsudestemg.demo.api.mapper;

import br.edu.ifsudestemg.demo.api.dto.BombaRequestDTO;
import br.edu.ifsudestemg.demo.api.dto.BombaResponseDTO;
import br.edu.ifsudestemg.demo.model.entity.Bomba;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BombaMapper {

    BombaResponseDTO toResponseDTO(Bomba entity);

    Bomba toEntity(BombaRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBombaFromDTO(BombaRequestDTO dto, @MappingTarget Bomba entity);
}
