package by.ganevich.io.commands;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
@AllArgsConstructor
@Getter
public class ReadBankAccountsCommand implements ICommand{

    private final String commandName = "read bankAccounts";

    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    //read bankAccounts Matvey
    @Override
    public Set<BankAccount> execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = clientService.findClientByName(parameters.get(0));

        Set<BankAccount> bankAccounts = bankAccountService.getAllAccountsOfClient(client);

        return bankAccounts;
    }
}
