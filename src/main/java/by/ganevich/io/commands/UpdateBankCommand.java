package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.Commission;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankService;
import by.ganevich.service.CommissionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class UpdateBankCommand implements ICommand {

    private final String commandName = "updateBank";

    private final BankService bankService;
    private final CommissionService commissionService;

    @Override
    public Bank execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "updateBank bankName=? newBankName=? " +
                    "newIndividualCommission=? newIndustrialCommission=?";

            System.out.println(help);
            return null;
        }

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

        return bank;
    }
}
