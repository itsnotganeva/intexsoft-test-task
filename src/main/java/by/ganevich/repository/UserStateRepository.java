package by.ganevich.repository;

import by.ganevich.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStateRepository extends JpaRepository<UserState, Long> {
    UserState findByState(String stateName);
}
