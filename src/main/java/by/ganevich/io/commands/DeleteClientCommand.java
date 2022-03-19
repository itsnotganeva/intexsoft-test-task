package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.service.ClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class DeleteClientCommand extends BaseCommand {

    private final String commandName = "deleteClient";
    private final ClientService clientService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String clientName;


    @Override
    public String getDescriptionValue() {
        String description = "deleteClient clientName=?";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        clientService.removeClient(client);

        CommandResult commandResult = new CommandResult();
        String result = "Client removed!";
        commandResult.setResult(result);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.clientName = commandDescriptor.getParameters().get("clientName");
        return this;
    }
}
