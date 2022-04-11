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
public class CreateClientCommand extends BaseCommand {

    private final String commandName = "createClient";
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Valid
    private ClientDto clientDto;

    @Override
    public String getDescriptionValue() {
        String description = "createClient clientName=? type=INDIVIDUAL/INDUSTRIAL";
        return description;
    }

    @Override
    public CommandResult doExecute(Map<String, String> parameters) {
        Client client = clientMapper.toEntity(clientDto);
        clientService.save(client);

        CommandResult commandResult = new CommandResult();
        commandResult.setResult(client);
        return commandResult;
    }

    @Override
    public ICommand setDto(CommandDescriptor commandDescriptor) {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(commandDescriptor.getParameters().get("clientName"));
        clientDto.setType(commandDescriptor.getParameters().get("type"));

        this.clientDto = clientDto;
        return this;
    }
}
