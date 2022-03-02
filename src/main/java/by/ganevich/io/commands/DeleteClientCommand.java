package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@AllArgsConstructor
@Getter
public class DeleteClientCommand implements ICommand {

    private final String commandName = "delete client";

    private final ClientService clientService;

    //delete client Matvey
    @Override
    public Client execute(CommandDescriptor commandDescriptor) {

        HashMap<Integer, String> parameters = commandDescriptor.getParameters();

        Client client = clientService.findClientByName(parameters.get(0));

        clientService.removeClient(client);

        return null;
    }

}
