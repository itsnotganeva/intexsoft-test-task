package by.ganevich.service;

import by.ganevich.entity.UserState;
import by.ganevich.repository.UserStateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserStateService {

    private final UserStateRepository userStateRepository;

    public UserState findByStateName(String stateName) {
        return userStateRepository.findByState(stateName);
    }
}
