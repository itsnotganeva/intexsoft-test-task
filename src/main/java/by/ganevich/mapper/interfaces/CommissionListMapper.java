package by.ganevich.mapper.interfaces;

import by.ganevich.dto.CommissionDto;
import by.ganevich.entity.Commission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommissionMapper.class)
public interface CommissionListMapper {
    List<Commission> toEntityList(List<CommissionDto> commissionsDto);
    List<CommissionDto> toDtoList(List<Commission> commissions);
}
