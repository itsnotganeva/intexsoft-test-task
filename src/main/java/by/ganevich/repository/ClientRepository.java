package by.ganevich.repository;

import by.ganevich.entity.Client;
import by.ganevich.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @EntityGraph(value = "clients-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    Client findByName(String name);

    @EntityGraph(value = "clients-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Client> findByNameAndAndSurname(String name, String surname);

    Optional<Client> findByUser(User user);
}
