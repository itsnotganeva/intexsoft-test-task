package by.ganevich.io.commands;

import by.ganevich.dto.BankDto;
import by.ganevich.dto.CommissionDto;
import by.ganevich.entity.Bank;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.mapper.interfaces.BankMapper;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Getter
@RequiredArgsConstructor
public class UpdateBankCommand extends BaseCommand {

    private final String commandName = "updateBank";

    private final BankService bankService;
    private final CommissionService commissionService;

    private final BankMapper bankMapper;

    @Valid
    private BankDto bankDto;

    @Override
    public String getDescriptionValue() {
        String description = "updateBank bankName=? newBankName=? "
                + "newIndividualCommission=? newIndustrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        CommandResult commandResult = new CommandResult();

        Bank bank = bankMapper.toEntity(bankDto);
        if (bank == null) {
            commandResult.setResult("The entered bank name does not exist!");
            return commandResult;
        }

        bankService.saveBank(bank);

        commandResult.setResult(bank);
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {

        BankDto bankDto = bankMapper
                .toDto(bankService.findBankByName(commandDescriptor.getParameters().get("bankName")));

        bankDto.setName(commandDescriptor.getParameters().get("newBankName"));

        CommissionDto individualCommission = new CommissionDto();
        individualCommission.setClientType(0);
        individualCommission.setCommission(commandDescriptor.getParameters().get("newIndividualCommission"));

        CommissionDto industrialCommission = new CommissionDto();
        industrialCommission.setClientType(0);
        industrialCommission.setCommission(commandDescriptor.getParameters().get("newIndustrialCommission"));

        Set<CommissionDto> commissionDtos = new HashSet<>();
        commissionDtos.add(individualCommission);
        commissionDtos.add(industrialCommission);
        bankDto.setCommissions(commissionDtos);

        this.bankDto = bankDto;
        return this;
    }
}
