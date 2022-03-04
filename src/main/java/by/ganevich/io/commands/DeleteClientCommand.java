package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@Getter
public class DeleteClientCommand implements ICommand {

    private final String commandName = "deleteClient";

    private final ClientService clientService;

    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        Map<String, String> parameters = commandDescriptor.getParameters();

        if (parameters.containsValue("help")){
            String help = "deleteClient clientName=?";
            System.out.println(help);
            return null;
        }

        Client client = clientService.findClientByName(parameters.get("clientName"));

        clientService.removeClient(client);

        return null;
    }

}
