package by.ganevich.service;

import by.ganevich.entity.Client;
import by.ganevich.repository.BankAccountRepository;
import by.ganevich.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> readClients() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    public Client findClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public void removeClient(Client client) {
        clientRepository.delete(client);
    }

    public Optional<Client> findClientById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

}
