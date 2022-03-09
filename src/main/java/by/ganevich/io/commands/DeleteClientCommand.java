package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class DeleteClientCommand extends BaseCommand {

    private final String commandName = "deleteClient";

    private final ClientService clientService;
    private final CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "deleteClient clientName=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        clientService.removeClient(client);

        commandResult.setT(null);
        return commandResult;
    }
}
