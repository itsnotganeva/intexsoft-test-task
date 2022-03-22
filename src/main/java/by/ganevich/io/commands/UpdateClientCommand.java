package by.ganevich.io.commands;

import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
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
public class UpdateClientCommand extends BaseCommand {

    private final String commandName = "updateClient";
    private final ClientService clientService;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String clientName;

    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
    @NotEmpty(message = "Name must not be empty")
    private String newClientName;

    @Pattern(regexp = "^individual$|^industrial$")
    @NotEmpty(message = "Type must not be empty")
    private String newType;


    @Override
    public String getDescriptionValue() {
        String description = "updateClient clientName=? newClientName=? newType=individual/industrial";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientService.findClientByName(parameters.get("clientName"));

        client.setName(parameters.get("newClientName"));

        if (parameters.get("newType").equals("individual")) {
            client.setType(ClientType.INDIVIDUAL);
        } else if (parameters.get("newType").equals("industrial")) {
            client.setType(ClientType.INDUSTRIAL);
        }

        clientService.saveClient(client);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(client);
        return commandResult;
    }

    @Override
    public ICommand setParameters(CommandDescriptor commandDescriptor) {
        this.clientName = commandDescriptor.getParameters().get("clientName");
        this.newClientName = commandDescriptor.getParameters().get("newClientName");
        this.newType = commandDescriptor.getParameters().get("newType");
        return this;
    }
}
