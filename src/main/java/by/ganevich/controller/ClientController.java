package by.ganevich.controller;

import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import by.ganevich.mapper.IMapper;
import by.ganevich.service.ClientService;
import by.ganevich.validator.EntityValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Client controller", description = "To manage clients")
public class ClientController {

    private final ClientService clientService;
    private final EntityValidator<Client> clientValidator;

    private final IMapper<ClientDto, Client> clientMapper;

    @PostMapping(value = "/clients")
    @Operation(
            summary = "Client creation",
            description = "Allows to create a new client"
    )
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "client to be added to the database")
                    ClientDto clientDto
    ) {
        Client client = clientMapper.toEntity(clientDto, Client.class);
        if (!clientValidator.validateEntity(client)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/clients")
    @Operation(
            summary = "Reading clients",
            description = "Allows to read all clients"
    )
    public ResponseEntity<List<ClientDto>> read() {
        final List<Client> clients = clientService.readClients();
        List<ClientDto> clientsDto = clientMapper.listToDto(clients, ClientDto.class);

        return new ResponseEntity<>(clientsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/clients/{id}")
    @Operation(
            summary = "Reading client",
            description = "Allows to read specific client by id"
    )
    public ResponseEntity<ClientDto> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        final Optional<Client> client = clientService.findClientById(id);
        ClientDto clientDto = clientMapper.optionalToDto(client, ClientDto.class);

        return clientDto != null
                ? new ResponseEntity<>(clientDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/clients/{id}")
    @Operation(
            summary = "Client update",
            description = "Allows to update specific client by id"
    )
    public ResponseEntity<?> update(
            @PathVariable(name = "id") @Parameter(description = "id of client to update") Long id,
            @RequestBody @Parameter(description = "updated client") ClientDto clientDto
    ) {
        Client client = clientMapper.toEntity(clientDto, Client.class);
        if (!clientValidator.validateEntity(client)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        clientService.saveClient(client);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/clients/{id}")
    @Operation(
            summary = "Client deletion",
            description = "Allows to delete specific client by id"
    )
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        clientService.deleteClientById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
