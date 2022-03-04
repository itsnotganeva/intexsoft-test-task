package by.ganevich.io.commands;

import by.ganevich.entity.Bank;
import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.entity.Currency;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.BankService;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class AddClientToBankCommand implements ICommand {

    private final String commandName = "addClientToBank";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    @Override
    public BankAccount execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "addClientToBank clientName=? bankName=? currency=? amountOfMoney=?";
            System.out.println(help);
            return null;
        }

        Client client = clientService.findClientByName(parameters.get("clientName"));
        Bank bank = bankService.findBankByName(parameters.get("bankName"));

        BankAccount newBankAccount = new BankAccount();

        if (parameters.get("currency").equals("usd")) {
            newBankAccount.setCurrency(Currency.USD);

        } else if (parameters.get("currency").equals("eur")) {
            newBankAccount.setCurrency(Currency.EUR);

        } else if (parameters.get("currency").equals("byn")) {
            newBankAccount.setCurrency(Currency.BYN);

        } else {
            throw new RuntimeException("CHOOSE usd/eur/byn TO SET A CURRENCY!");
        }

        newBankAccount.setAmountOfMoney(Double.valueOf(parameters.get("amountOfMoney")));

        newBankAccount.setOwner(client);
        newBankAccount.setBankProducer(bank);

        clientService.saveClient(client);
        bankService.saveBank(bank);

        bankAccountService.saveBankAccount(newBankAccount);
        return newBankAccount;
    }

}
