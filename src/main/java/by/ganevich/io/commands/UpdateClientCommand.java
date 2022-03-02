package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class UpdateClientCommand implements ICommand {

    private final String commandName = "update client";

    private final ClientService clientService;

    //update client Matvey Matvey industrial
    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = clientService.findClientByName(parameters.get(0));

        client.setName(parameters.get(1));

        if (parameters.get(2).equals("individual")) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (parameters.get(2).equals("industrial")) {
            client.setType(ClientType.INDUSTRIAL);
        } else {
            throw new RuntimeException("CHOOSE individual OR industrial TO SET A TYPE!");
        }

        clientService.saveClient(client);

        return client;
    }
}
