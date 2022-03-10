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

    @Override
    public String getDescriptionValue() {
        String description = "readBankAccounts clientName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        Set<BankAccount> bankAccounts = bankAccountService.getAllAccountsOfClient(client);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(bankAccounts);
        return commandResult;
    }

}
