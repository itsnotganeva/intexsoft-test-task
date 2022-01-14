package by.ganevich.repo;

import by.ganevich.entity.Client;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ClientRepoTest {

   // @Test
    public void testClientRepoInsertGet() {
        ClientRepo instance = ClientRepo.getInstance();

        Client client = new Client();
        client.setName("Matvey");
      //  client.setType("individual");

        instance.save(client);

        Client client2 = instance.getById(client.getId());
        Assertions.assertEquals(client, client2);

        client.setName("Igor");

        instance.save(client);

        Client clientAfterUpdate = instance.getById(client.getId());

        Assertions.assertEquals("Igor", clientAfterUpdate.getName());

        List<Client> clients = instance.findAll();

        Assertions.assertEquals(client, clients.get(0));

        instance.deleteById(client.getId());

        Assertions.assertNull(instance.getById(client.getId()));


    }
}
