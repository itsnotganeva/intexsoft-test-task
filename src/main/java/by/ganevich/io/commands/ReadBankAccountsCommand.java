package by.ganevich.io.commands;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandResult;
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
public class ReadBankAccountsCommand extends BaseCommand {

    private final String commandName = "readBankAccounts";

    private final BankAccountService bankAccountService;
    private final ClientService clientService;
    private CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "readBankAccounts clientName=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        Set<BankAccount> bankAccounts = bankAccountService.getAllAccountsOfClient(client);

        commandResult.setT(bankAccounts);
        return commandResult;
    }

}
