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

    @Override
    public String getDescriptionValue() {
        String description = "createClient clientName=? type=?";
        return description;
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

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(client);
        return commandResult;
    }

}
