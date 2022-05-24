package by.ganevich.service;

import by.ganevich.entity.Client;
import by.ganevich.entity.enums.ClientType;
import by.ganevich.repository.ClientRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    private static List<Client> testClients;

    @BeforeEach
    public void prepareTestData()
    {
        testClients = new ArrayList<>();

        Client client = new Client();
        client.setName("Test");
        client.setType(ClientType.INDIVIDUAL);

        Client client2 = new Client();
        client2.setName("Client");
        client2.setType(ClientType.INDUSTRIAL);

        testClients.add(client);
        testClients.add(client2);
    }

    @BeforeEach
    public void init()
    {
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void findClientByName_Should_Return_Client()
    {
        when(clientRepository.findByName(any(String.class)))
                .thenReturn(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());

        Client resultClient = clientService.findClientByName("Test");

        Assert.assertNotNull(resultClient);
        Assert.assertTrue(resultClient.getName().equals("Test"));
    }

    @Test
    public void findClientByName_Should_Return_Null() {

        when(clientRepository.findByName("invalidName")).thenReturn(null);

        Client resultClient = clientService.findClientByName("invalidName");

        Assert.assertTrue(resultClient == null);
    }

    @Test
    public void saveClient()
    {
        when(clientRepository.save(any(Client.class)))
                .thenReturn(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());

        Client resultClient = clientService.save(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());

        Assert.assertNotNull(resultClient);
        Assert.assertTrue(resultClient.getName().equals("Test"));
    }

    @Test
    public void removeClient()
    {
        doNothing().when(clientRepository)
                .delete(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());

        clientService
                .removeClient(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());

        verify(clientRepository, times(1))
                .delete(testClients.stream().filter(client -> client.getName().equals("Test")).findFirst().get());
    }

    @Test
    public void getAll()
    {
        when(clientRepository.findAll()).thenReturn(testClients);

        List<Client> clients = clientService.readAll();

        Assert.assertNotNull(clients);
        Assert.assertEquals(testClients, clients);
    }

}
