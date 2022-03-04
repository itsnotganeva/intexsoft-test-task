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
public class CreateClientCommand implements ICommand{

    private final String commandName = "createClient";

    private final ClientService clientService;

    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "createClient clientName=? type=?";
            System.out.println(help);
            return null;
        }

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

        return client;
    }


}
