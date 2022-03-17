package by.ganevich.mapper.interfaces;

import by.ganevich.dto.BankDto;
import by.ganevich.entity.Bank;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CommissionListMapper.class)
public interface BankMapper {
    BankDto toDto(Bank bank);
    Bank toEntity(BankDto bankDto);
}
