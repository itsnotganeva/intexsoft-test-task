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
public class UpdateClientCommand extends BaseCommand {

    private final String commandName = "updateClient";

    private final ClientService clientService;
    private final CommandResult commandResult;

    @Override
    public CommandResult getDescription() {
        String description = "updateClient clientName=? newClientName=? newType=individual/industrial";
        commandResult.setT(description);
        return commandResult;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        client.setName(parameters.get("newClientName"));

        if (parameters.get("newType").equals("individual")) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (parameters.get("newType").equals("industrial")) {
            client.setType(ClientType.INDUSTRIAL);
        } else {
            throw new RuntimeException("CHOOSE individual OR industrial TO SET A TYPE!");
        }

        clientService.saveClient(client);

        commandResult.setT(client);
        return commandResult;
    }

}
