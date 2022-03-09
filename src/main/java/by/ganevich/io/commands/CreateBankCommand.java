package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class CreateBankCommand extends BaseCommand {

    private final BankService bankService;
    private final CommissionService commissionService;
    private final CommandResult commandResult;

    private final String commandName = "createBank";

    @Override
    public CommandResult getDescription() {
        String description = "createBank bankName=? individualCommission=? industrialCommission=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = new Bank();
        bank.setName(parameters.get("bankName"));

        bankService.saveBank(bank);

        Commission individualCommission = new Commission();
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(Double.valueOf(parameters.get("individualCommission")));
        individualCommission.setBank(bank);
        commissionService.saveCommission(individualCommission);

        Commission industrialCommission = new Commission();
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(Double.valueOf(parameters.get("industrialCommission")));
        industrialCommission.setBank(bank);
        commissionService.saveCommission(industrialCommission);

        commandResult.setT(bank);
        return commandResult;
    }
}
