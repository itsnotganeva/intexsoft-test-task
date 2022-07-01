package by.ganevich.io.commands;

import by.ganevich.dto.BankAccountDto;
import by.ganevich.dto.TransactionToViewDto;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Transaction;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.mapper.interfaces.BankAccountMapper;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
@RequiredArgsConstructor
public class ReadTransactionsCommand extends BaseCommand {

    private final String commandName = "readTransactions";

    private final TransactionService transactionService;
    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @Valid
    private TransactionToViewDto transactionToViewDto;

    @Override
    public String getDescriptionValue() {
        String description = "readTransactions type=sent/received accountNumber=? "
                + "dateBefore=YYYY-MM-DD dateAfter=YYYY-MM-DD";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        log.info("Read transactions command is called");

        CommandResult commandResult = new CommandResult();
        List<Transaction> transactions = new ArrayList<>();

        BankAccount bankAccount = bankAccountMapper.toEntity(transactionToViewDto.getBankAccountDto());
        Date dateBefore = Date.valueOf(transactionToViewDto.getDateBefore());
        Date dateAfter = Date.valueOf(transactionToViewDto.getDateAfter());

        if (transactionToViewDto.getType().equals("sent")) {
            transactions =
                    transactionService.readAllByDateAndSenderAccount(dateBefore, dateAfter, bankAccount);
        } else if (transactionToViewDto.getType().equals("received")) {
            transactions =
                    transactionService.readAllByDateAndReceiverAccount(dateBefore, dateAfter, bankAccount);
        }

        log.info("Read transactions command is complete");

        commandResult.setResult(transactions);
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        TransactionToViewDto transactionToViewDto = new TransactionToViewDto();
        BankAccountDto bankAccountDto = bankAccountMapper
                .toDto(bankAccountService.findBankAccountByNumber(Integer
                        .valueOf(commandDescriptor.getParameters().get("accountNumber"))));

        transactionToViewDto.setBankAccountDto(bankAccountDto);
        transactionToViewDto.setType(commandDescriptor.getParameters().get("type"));
        transactionToViewDto.setDateBefore(commandDescriptor.getParameters().get("dateBefore"));
        transactionToViewDto.setDateAfter(commandDescriptor.getParameters().get("dateAfter"));

        this.transactionToViewDto = transactionToViewDto;
        return this;
    }
}
