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

    private Client getClient(List<Client> clients) {
        return clients.stream().filter(client -> client.getName().equals("Test")).findFirst().get();
    }

    @Test
    public void findClientByName_Should_Return_Client()
    {
        when(clientRepository.findByName(any(String.class)))
                .thenReturn(getClient(testClients));

        Client resultClient = clientService.findClientByName("Test");

        Assert.assertNotNull(resultClient);
        Assert.assertTrue(resultClient.getName().equals("Test"));
        Assert.assertEquals(getClient(testClients).getName(), resultClient.getName());
        Assert.assertEquals(getClient(testClients).getType(), resultClient.getType());
    }

    @Test
    public void findClientByName_Should_Return_Null() {

        when(clientRepository.findByName("invalidName")).thenReturn(null);

        Client resultClient = clientService.findClientByName("invalidName");

        Assert.assertTrue(resultClient == null);
    }

    @Test
    public void saveClient_Should_Return_Client()
    {
        when(clientRepository.save(any(Client.class)))
                .thenReturn(getClient(testClients));

        Client resultClient = clientService.save(getClient(testClients));

        Assert.assertNotNull(resultClient);
        Assert.assertTrue(resultClient.getName().equals("Test"));
        Assert.assertEquals(getClient(testClients).getName(), resultClient.getName());
        Assert.assertEquals(getClient(testClients).getType(), resultClient.getType());
    }

    @Test
    public void removeClient()
    {
        doNothing().when(clientRepository)
                .delete(getClient(testClients));

        clientService
                .removeClient(getClient(testClients));

        verify(clientRepository, times(1))
                .delete(getClient(testClients));
    }

    @Test
    public void getAll_Should_Return_Clients()
    {
        when(clientRepository.findAll()).thenReturn(testClients);

        List<Client> clients = clientService.readAll();

        Assert.assertNotNull(clients);
        Assert.assertEquals(testClients, clients);
    }

}
