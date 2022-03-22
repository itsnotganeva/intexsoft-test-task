package by.ganevich.io.commands;

import by.ganevich.dto.BankDto;
import by.ganevich.dto.CommissionDto;
import by.ganevich.entity.Bank;
import by.ganevich.entity.Commission;
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
public class CreateBankCommand extends BaseCommand {

    private final BankService bankService;
    private final CommissionService commissionService;
    private final BankMapper bankMapper;

    @Valid
    private BankDto bankDto;

    private final String commandName = "createBank";

    @Override
    public String getDescriptionValue() {
        String description = "createBank bankName=? individualCommission=? industrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        Bank bank = bankMapper.toEntity(bankDto);

        bankService.saveBank(bank);

        Set<Commission> commissions = bank.getCommissions();

        for (Commission c : commissions) {
            c.setBank(bank);
            commissionService.saveCommission(c);
        }

        CommandResult commandResult = new CommandResult();
        commandResult.setResult("Bank is successfully created!");

        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        BankDto bankDto = new BankDto();
        bankDto.setName(commandDescriptor.getParameters().get("bankName"));

        CommissionDto individualCommissionDto = new CommissionDto();
        individualCommissionDto.setClientType(0);
        individualCommissionDto.setCommission(commandDescriptor.getParameters().get("individualCommission"));

        CommissionDto industrialCommissionDto = new CommissionDto();
        industrialCommissionDto.setClientType(1);
        industrialCommissionDto.setCommission(commandDescriptor.getParameters().get("industrialCommission"));

        Set<CommissionDto> commissionsDto = new HashSet<>();
        commissionsDto.add(individualCommissionDto);
        commissionsDto.add(industrialCommissionDto);

        bankDto.setCommissions(commissionsDto);

        this.bankDto = bankDto;

        return this;
    }
}
