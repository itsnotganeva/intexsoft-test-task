package by.ganevich.mapper.interfaces;

import by.ganevich.dto.BankDto;
import by.ganevich.entity.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BankMapper.class)
public interface BankListMapper {
    List<Bank> toEntityList(List<BankDto> banksDto);
    List<BankDto> toDtoList(List<Bank> banks);
}
