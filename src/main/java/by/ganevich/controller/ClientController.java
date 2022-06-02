package by.ganevich.controller;

import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import by.ganevich.mapper.interfaces.ClientMapper;
import by.ganevich.service.ClientService;
import by.ganevich.validator.CustomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Client controller", description = "To manage clients")
public class ClientController {

    private final ClientService clientService;
    private final CustomValidator<ClientDto> clientValidator;

    private final ClientMapper clientMapper;

    @PostMapping(value = "/clients")
    @Operation(
            summary = "Client creation",
            description = "Allows to create a new client"
    )
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "client to be added to the database")
                    ClientDto clientDto
    ) {
        log.info("REST: Create client is called");
        if (!clientValidator.validateDto(clientDto)) {
            log.info("REST: Input of client data is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Client client = clientMapper.toEntity(clientDto);
        clientService.save(client);
        log.info("REST: Creating of client was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping(value = "/clients")
    @Operation(
            summary = "Reading clients",
            description = "Allows to read all clients"
    )
    public ResponseEntity<List<ClientDto>> read() {
        log.info("REST: Read clients is called");
        final List<Client> clients = clientService.readAll();
        List<ClientDto> clientsDto = clientMapper.toDtoList(clients);
        log.info("REST: Reading of clients was successful");
        return new ResponseEntity<>(clientsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    @GetMapping(value = "/clients/{id}")
    @Operation(
            summary = "Reading client",
            description = "Allows to read specific client by id"
    )
    public ResponseEntity<ClientDto> read(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        log.info("REST: Read client with id" + id + " is called");
        final Optional<Client> client = clientService.findClientById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ClientDto clientDto = clientMapper.toDto(client.get());

            log.info("REST: Readinf of client with id" + id + " was successful");
            return new ResponseEntity<>(clientDto, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR', 'ROLE_CLIENT', 'ROLE_ADMIN')")
    @PutMapping(value = "/clients/{id}")
    @Operation(
            summary = "Client update",
            description = "Allows to update specific client by id"
    )
    public ResponseEntity<?> update(
            @PathVariable(name = "id") @Parameter(description = "id of client to update") Long id,
            @RequestBody @Parameter(description = "updated client") ClientDto clientDto
    ) {
        log.info("REST: Update client with id" + id + " is called");
        if (!clientValidator.validateDto(clientDto)) {
            log.error("REST: The new input data of client with id" + id + " is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Client client = clientMapper.toEntity(clientDto);
        clientService.save(client);
        log.info("REST: Updating of client with id" + id + " was successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    @DeleteMapping(value = "/clients/{id}")
    @Operation(
            summary = "Client deletion",
            description = "Allows to delete specific client by id"
    )
    public ResponseEntity<?> delete(
            @PathVariable(name = "id") @Parameter(description = "id of client") Long id
    ) {
        log.info("REST: Delete client with id" + id + " is called");
        clientService.deleteClientById(id);

        log.info("REST: Client with id" + id + " was removed successful");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
