package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
import by.ganevich.validator.EntityValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
@Validated
public class CreateBankCommand extends BaseCommand {

    private final BankService bankService;
    private final CommissionService commissionService;
    private final EntityValidator<Bank> bankValidator;
    private final EntityValidator<Commission> commissionValidator;


    private final String commandName = "createBank";

    @Override
    public String getDescriptionValue() {
        String description = "createBank bankName=? individualCommission=? industrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {

        Bank bank = new Bank();
        bank.setName(parameters.get("bankName"));

        if (!bankValidator.validateEntity(bank)) {
            return null;
        }

        bankService.saveBank(bank);

        Commission individualCommission = new Commission();
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());

        Commission industrialCommission = new Commission();
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());

        individualCommission.setCommission(Double.valueOf(parameters.get("individualCommission")));
        individualCommission.setBank(bank);
        industrialCommission.setCommission(Double.valueOf(parameters.get("industrialCommission")));
        industrialCommission.setBank(bank);

        if (!commissionValidator.validateEntity(individualCommission) || commissionValidator.validateEntity(industrialCommission)) {
            return null;
        }

        commissionService.saveCommission(industrialCommission);
        commissionService.saveCommission(individualCommission);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(bank);
        return commandResult;
    }
}
