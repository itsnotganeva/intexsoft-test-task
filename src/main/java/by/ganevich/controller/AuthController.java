package by.ganevich.controller;


import by.ganevich.config.security.jwt.JwtProvider;
import by.ganevich.dto.RegistrationRequestDto;
import by.ganevich.dto.VerifyUserDto;
import by.ganevich.entity.Client;
import by.ganevich.entity.ClientType;
import by.ganevich.entity.User;
import by.ganevich.entity.UserState;
import by.ganevich.mail.EmailService;
import by.ganevich.service.ClientService;
import by.ganevich.service.UserService;
import by.ganevich.service.UserStateService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthController {

    private final ClientService clientService;
    private UserService userService;
    private JwtProvider jwtProvider;
    private final EmailService emailService;
    private final UserStateService userStateService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequestDto registrationRequest) throws MessagingException {

        UserState userState = userStateService.findByStateName("NOT ACTIVATED");
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        user.setState(userState);
        userService.saveUser(user, "ROLE_CLIENT");

        Client client = new Client();
        client.setName(registrationRequest.getName());
        client.setType(ClientType.valueOf(registrationRequest.getType()));
        client.setUser(user);
        clientService.save(client);

        user.setCode(emailService.sendEmail(registrationRequest));
        userService.saveUser(user);
        return "OK";
    }

    @PostMapping("/verify/{id}")
    public String verifyUser(@PathVariable(name = "id") @Parameter(description = "id of user") Long id,
                             @RequestBody VerifyUserDto verifyUserDto) {

        Optional<User> user = userService.findById(id);
        if (verifyUserDto.getNumber().equals(user.get().getCode())) {
            UserState userState = userStateService.findByStateName("ACTIVATED");
            user.get().setState(userState);
            user.get().setCode(null);
            userService.saveUser(user.get());
            return "OK";
        } else return "Security code is not correct";

    }


    @PostMapping("/auth")
    public String auth(@RequestBody RegistrationRequestDto request) {
        User userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        UserState userState = userStateService.findByStateName("NOT ACTIVATED");
        if (userEntity.getState().equals(userState)) {
            return "You are not activated!";
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return token;
    }
}
