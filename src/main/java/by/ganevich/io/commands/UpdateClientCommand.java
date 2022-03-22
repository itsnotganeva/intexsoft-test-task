package by.ganevich.io.commands;

import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import by.ganevich.io.CommandDescriptor;
import by.ganevich.io.CommandResult;
import by.ganevich.mapper.interfaces.ClientMapper;
import by.ganevich.service.ClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class UpdateClientCommand extends BaseCommand {

    private final String commandName = "updateClient";
    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @Valid
    private ClientDto clientDto;

//    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
//    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
//    @NotEmpty(message = "Name must not be empty")
//    private String clientName;
//
//    @Pattern(regexp = "[A-Z][a-z]*", message = "Client name must start with a capital letter")
//    @Size(min = 2, max = 25, message = "Name length must be between 2 and 25")
//    @NotEmpty(message = "Name must not be empty")
//    private String newClientName;
//
//    @Pattern(regexp = "^individual$|^industrial$")
//    @NotEmpty(message = "Type must not be empty")
//    private String newType;


    @Override
    public String getDescriptionValue() {
        String description = "updateClient clientName=? newClientName=? newType=INDIVIDUAL/INDUSTRIAL";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientMapper.toEntity(clientDto);
        clientService.saveClient(client);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(client);
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        ClientDto clientDto = clientMapper
                .toDto(clientService.findClientByName(commandDescriptor.getParameters().get("clientName")));

        clientDto.setName(commandDescriptor.getParameters().get("newClientName"));
        clientDto.setType(commandDescriptor.getParameters().get("newType"));

        this.clientDto = clientDto;
        return this;
    }
}
