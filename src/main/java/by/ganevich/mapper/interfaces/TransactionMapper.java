package by.ganevich.mapper.interfaces;

import by.ganevich.dto.TransactionDto;
import by.ganevich.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = BankAccountMapper.class)
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);

    Transaction toEntity(TransactionDto transactionDto);

    List<Transaction> toEntityList(List<TransactionDto> transactionsDto);

    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
