package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class UpdateClientCommand implements ICommand {

    private final String commandName = "updateClient";

    private final ClientService clientService;

    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "updateClient clientName=? newClientName=? newType=individual/industrial";

            System.out.println(help);
            return null;
        }

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

        return client;
    }
}
