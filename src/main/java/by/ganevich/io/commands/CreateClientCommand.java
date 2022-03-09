package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class CreateClientCommand extends BaseCommand {

    private final String commandName = "createClient";

    private final ClientService clientService;
    private final CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "createClient clientName=? type=?";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = new Client();
        client.setName(parameters.get("clientName"));

        if (parameters.get("type").equals("individual")) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (parameters.get("type").equals("industrial")) {
            client.setType(ClientType.INDUSTRIAL);
        } else {
            throw new RuntimeException("CHOOSE individual OR industrial TO SET A TYPE!");
        }

        clientService.saveClient(client);

        commandResult.setT(client);
        return commandResult;
    }

}
