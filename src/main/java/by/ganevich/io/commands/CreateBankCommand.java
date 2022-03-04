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
public class CreateBankCommand implements ICommand{

    private final BankService bankService;
    private final CommissionService commissionService;

    private final String commandName = "createBank";

    @Override
    public Bank execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "createBank bankName=? individualCommission=? industrialCommission=?";
            System.out.println(help);
            return null;
        }

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

        return bank;
    }


}
