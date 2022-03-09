package by.ganevich.io.commands;

import by.ganevich.entity.Client;
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
    public String getDescription() {
        String description = "deleteClient clientName=?";
        return description;
    }

    @Override
    public Object doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        clientService.removeClient(client);

        return null;
    }
}
