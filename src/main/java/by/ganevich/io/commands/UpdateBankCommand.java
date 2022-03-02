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

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class UpdateBankCommand implements ICommand {

    private final String commandName = "update bank";

    private final BankService bankService;
    private final CommissionService commissionService;

    //update bank Alfa Alfa 0.015 0.03
    @Override
    public Bank execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Bank bank = bankService.findBankByName(parameters.get(0));

        bank.setName(parameters.get(1));
        bankService.saveBank(bank);

        Commission individualCommission = commissionService.findByBankAndClientType(bank, ClientType.INDIVIDUAL);
        individualCommission.setClientType(ClientType.INDIVIDUAL.ordinal());
        individualCommission.setCommission(Double.valueOf(parameters.get(2)));
        individualCommission.setBank(bank);
        commissionService.saveCommission(individualCommission);

        Commission industrialCommission = commissionService.findByBankAndClientType(bank, ClientType.INDUSTRIAL);
        industrialCommission.setClientType(ClientType.INDUSTRIAL.ordinal());
        industrialCommission.setCommission(Double.valueOf(parameters.get(3)));
        industrialCommission.setBank(bank);
        commissionService.saveCommission(industrialCommission);

        return bank;
    }
}
