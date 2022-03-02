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

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class AddClientToBankCommand implements ICommand {

    private final String commandName = "add client";

    private final ClientService clientService;
    private final BankService bankService;
    private final BankAccountService bankAccountService;

    //add client Matvey Alfa usd 500
    @Override
    public BankAccount execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = clientService.findClientByName(parameters.get(0));
        Bank bank = bankService.findBankByName(parameters.get(1));

        BankAccount newBankAccount = new BankAccount();

        if (parameters.get(2).equals("usd")) {
            newBankAccount.setCurrency(Currency.USD);

        } else if (parameters.get(2).equals("eur")) {
            newBankAccount.setCurrency(Currency.EUR);

        } else if (parameters.get(2).equals("byn")) {
            newBankAccount.setCurrency(Currency.BYN);

        } else {
            throw new RuntimeException("CHOOSE usd/eur/byn TO SET A CURRENCY!");
        }

        newBankAccount.setAmountOfMoney(Double.valueOf(parameters.get(3)));

        newBankAccount.setOwner(client);
        newBankAccount.setBankProducer(bank);

        clientService.saveClient(client);
        bankService.saveBank(bank);

        bankAccountService.saveBankAccount(newBankAccount);
        return newBankAccount;
    }

}
