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

    @Override
    public String getDescriptionValue() {
        String description = "deleteClient clientName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        clientService.removeClient(client);

        CommandResult commandResult = new CommandResult();
        String result = "Client removed!";
        commandResult.setResult(result);
        return commandResult;
    }
}
