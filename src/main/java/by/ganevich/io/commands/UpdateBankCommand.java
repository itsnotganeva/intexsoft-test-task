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

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class UpdateBankCommand extends BaseCommand {

    private final String commandName = "updateBank";

    private final BankService bankService;
    private final CommissionService commissionService;
    private final EntityValidator<Bank> bankValidator;
    private final EntityValidator<Commission> commissionValidator;

    @Override
    public String getDescriptionValue() {
        String description = "updateBank bankName=? newBankName=? "
                + "newIndividualCommission=? newIndustrialCommission=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bank.setName(parameters.get("newBankName"));

        if (!bankValidator.validateEntity(bank)){
            return null;
        }
        bankService.saveBank(bank);

        Commission individualCommission = commissionService.findByBankAndClientType(bank, ClientType.INDIVIDUAL);
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(Double.valueOf(parameters.get("newIndividualCommission")));
        individualCommission.setBank(bank);

        Commission industrialCommission = commissionService.findByBankAndClientType(bank, ClientType.INDUSTRIAL);
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(Double.valueOf(parameters.get("newIndustrialCommission")));
        industrialCommission.setBank(bank);

        if (!commissionValidator.validateEntity(individualCommission) || commissionValidator.validateEntity(industrialCommission)) {
            return null;
        }
        commissionService.saveCommission(individualCommission);
        commissionService.saveCommission(industrialCommission);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(bank);
        return commandResult;
    }

}
