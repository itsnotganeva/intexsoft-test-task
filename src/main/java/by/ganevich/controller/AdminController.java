package by.ganevich.controller;

import by.ganevich.dto.RegistrationRequestDto;
import by.ganevich.entity.User;
import by.ganevich.entity.enums.Role;
import by.ganevich.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Admin controller", description = "Admin-only controller")
public class AdminController {

    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/operator")
    @Operation(
            summary = "Operator registration",
            description = "Allows to register a new operator by admin"
    )
    public ResponseEntity<?> registerOperator(@RequestBody @Parameter(description = "Data to register a new operator")
                                                  RegistrationRequestDto registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveUser(user, Role.ROLE_OPERATOR);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
