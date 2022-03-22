package by.ganevich.io.commands;

import by.ganevich.entity.BankAccount;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.BankAccountService;
import by.ganevich.service.ClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.Set;

@Component
@Getter
@RequiredArgsConstructor
public class ReadBankAccountsCommand extends BaseCommand {

    private final String commandName = "readBankAccounts";

    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String clientName;


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

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.clientName = commandDescriptor.getParameters().get("clientName");
        return this;
    }
}
