package by.ganevich.service;

import by.ganevich.entity.User;
import by.ganevich.entity.enums.Role;
import by.ganevich.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user, Role role) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
            user.getRoles().add(role.toString());
        } else {
            user.getRoles().add(role.toString());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User findByLoginAndPassword(String login, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
