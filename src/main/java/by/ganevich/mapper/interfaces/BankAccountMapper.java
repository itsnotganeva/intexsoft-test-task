package by.ganevich.mapper.interfaces;

import by.ganevich.dto.BankAccountDto;
import by.ganevich.entity.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, BankMapper.class} )
public interface BankAccountMapper {
    BankAccountDto toDto(BankAccount bankAccount);
    BankAccount toEntity(BankAccountDto bankAccountDto);
}
