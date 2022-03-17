package by.ganevich.mapper.interfaces;

import by.ganevich.dto.CommissionDto;
import by.ganevich.entity.Commission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommissionMapper {
    CommissionDto toDto(Commission commission);
    Commission toEntity(CommissionDto commissionDto);
}
