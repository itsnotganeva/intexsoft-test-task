package by.ganevich.controller;

import by.ganevich.dto.RegistrationRequestDto;
import by.ganevich.entity.User;
import by.ganevich.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdminController {

    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/register/operator")
    public String registerOperator(@RequestBody RegistrationRequestDto registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user, "ROLE_OPERATOR");

        return "OK";
    }
}
