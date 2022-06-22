package by.ganevich.controller;


import by.ganevich.config.security.jwt.JwtProvider;
import by.ganevich.dto.AuthRequestDto;
import by.ganevich.dto.AuthResponseDto;
import by.ganevich.dto.RegistrationRequestDto;
import by.ganevich.dto.VerifyUserDto;
import by.ganevich.entity.Client;
import by.ganevich.entity.User;
import by.ganevich.entity.enums.ClientType;
import by.ganevich.entity.enums.Role;
import by.ganevich.entity.enums.State;
import by.ganevich.mail.EmailService;
import by.ganevich.service.ClientService;
import by.ganevich.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Auth controller", description = "To manage authorisation of users")
public class AuthController {

    private final ClientService clientService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;


    @PostMapping("/register")
    @Operation(
            summary = "User registration",
            description = "Allows to register a new user"
    )
    public ResponseEntity<?> registerUser(
            @RequestBody @Parameter(description = "Data to register a new user")
            RegistrationRequestDto registrationRequest
    ) throws MessagingException {

        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        user.setState(State.NOT_ACTIVATED);
        userService.saveUser(user, Role.ROLE_CLIENT);

        Client client = new Client();
        client.setName(registrationRequest.getName());
        client.setSurname(registrationRequest.getSurname());
        client.setType(ClientType.valueOf(registrationRequest.getType()));
        client.setUser(user);
        clientService.save(client);

        emailService.invokeEmailSender(user, registrationRequest);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify/{id}")
    @Operation(
            summary = "Verifying users",
            description = "Verify user to being activated"
    )
    public ResponseEntity<?> verifyUser(@PathVariable(name = "id") @Parameter(description = "id of user") Long id,
                                        @RequestBody @Parameter(description = "Secret code from mail")
                                        VerifyUserDto verifyUserDto) {

        Optional<User> user = userService.findById(id);
        if (verifyUserDto.getNumber().equals(user.get().getCode())) {
            user.get().setState(State.ACTIVATED);
            user.get().setCode(null);
            userService.saveUser(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("/auth")
    @Operation(
            summary = "User authorisation",
            description = "Allows to log in to user"
    )
    public ResponseEntity<AuthResponseDto> auth(@RequestBody @Parameter(description = "Data to log in")
                                                AuthRequestDto request) {
        User userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (userEntity.getState().equals(State.NOT_ACTIVATED)) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        AuthResponseDto authResponseDto = new AuthResponseDto(token);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
