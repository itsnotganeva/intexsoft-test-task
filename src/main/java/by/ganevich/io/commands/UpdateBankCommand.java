package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
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
public class UpdateBankCommand extends BaseCommand {

    private final String commandName = "updateBank";

    private final BankService bankService;
    private final CommissionService commissionService;
    private final CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "updateBank bankName=? newBankName=? "
                + "newIndividualCommission=? newIndustrialCommission=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        bank.setName(parameters.get("newBankName"));
        bankService.saveBank(bank);

        Commission individualCommission = commissionService.findByBankAndClientType(bank, ClientType.INDIVIDUAL);
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(Double.valueOf(parameters.get("newIndividualCommission")));
        individualCommission.setBank(bank);
        commissionService.saveCommission(individualCommission);

        Commission industrialCommission = commissionService.findByBankAndClientType(bank, ClientType.INDUSTRIAL);
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(Double.valueOf(parameters.get("newIndustrialCommission")));
        industrialCommission.setBank(bank);
        commissionService.saveCommission(industrialCommission);

        commandResult.setT(bank);
        return commandResult;
    }

}
