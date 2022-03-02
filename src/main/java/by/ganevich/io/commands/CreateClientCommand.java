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
public class CreateClientCommand implements ICommand{

    private final String commandName = "create client";

    private final ClientService clientService;

    //create client Matvey individual
    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = new Client();
        client.setName(parameters.get(0));

        if (parameters.get(1).equals("individual")) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (parameters.get(1).equals("industrial")) {
            client.setType(ClientType.INDUSTRIAL);
        } else {
            throw new RuntimeException("CHOOSE individual OR industrial TO SET A TYPE!");
        }

        clientService.saveClient(client);

        return client;
    }


}
