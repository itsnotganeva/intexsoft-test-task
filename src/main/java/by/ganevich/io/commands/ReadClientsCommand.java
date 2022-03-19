package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Getter
public class ReadClientsCommand implements ICommand {

    private final String commandName = "readClients";

    private final ClientService clientService;

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        return null;
    }

    @Override
    public CommandResult execute(CommandDescriptor commandDescriptor) {

        List<Client> clients = clientService.readClients();

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(clients);
        return commandResult;
    }
}
