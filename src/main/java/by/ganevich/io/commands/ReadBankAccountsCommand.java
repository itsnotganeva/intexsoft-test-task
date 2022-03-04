package by.ganevich.io.commands;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
@Getter
public class ReadBankAccountsCommand implements ICommand{

    private final String commandName = "readBankAccounts";

    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    @Override
    public Set<BankAccount> execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")) {
            String help = "readBankAccounts clientName=?";
            System.out.println(help);
            return null;
        }

        Client client = clientService.findClientByName(parameters.get("clientName"));

        Set<BankAccount> bankAccounts = bankAccountService.getAllAccountsOfClient(client);

        return bankAccounts;
    }
}
