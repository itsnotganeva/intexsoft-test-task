package by.ganevich.controller;

import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import by.ganevich.entity.enums.ClientType;
import by.ganevich.mapper.interfaces.ClientMapper;
import by.ganevich.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClientMapper clientMapper;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @MockBean
    private ClientService clientService;

    private List<Client> clients;

    @BeforeEach
    public void prepareTestData()
    {
        clients = new ArrayList<>();

        Client client = new Client();
        client.setName("Test");
        client.setType(ClientType.INDIVIDUAL);
        clients.add(client);
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void readAll() throws Exception
    {
        when(clientService.readAll()).thenReturn(clients);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients").accept(MediaType.APPLICATION_JSON);

        List<ClientDto> clientsDto = clientMapper.toDtoList(clients);
        String expectedJson = new ObjectMapper().writeValueAsString(clientsDto);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void readById_Should_Return_Client() throws Exception
    {
        when(clientService.findClientById(any(Long.class))).thenReturn(clients.stream().findFirst());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients/1").accept(MediaType.APPLICATION_JSON);

        ClientDto clientDto = clientMapper.toDto(clients.stream().findFirst().get());
        String expectedJson = new ObjectMapper().writeValueAsString(clientDto);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void readById_Should_Return_Not_Found() throws Exception
    {
        when(clientService.findClientById(any(Long.class))).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/clients/2").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void create_Should_Return_Status_Ok() throws Exception
    {
        when(clientService.save(any(Client.class))).thenReturn(clients.stream().findFirst().get());

        ClientDto clientDto = clientMapper.toDto(clients.stream().findFirst().get());
        String expectedJson = new ObjectMapper().writeValueAsString(clientDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/clients")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void create_Should_Return_Status_Bad_Request() throws Exception
    {
        when(clientService.save(any(Client.class))).thenReturn(clients.stream().findFirst().get());

        Client invalidClient = clients.stream().findFirst().get();
        invalidClient.setName("invalid");

        ClientDto clientDto = clientMapper.toDto(invalidClient);
        String expectedJson = new ObjectMapper().writeValueAsString(clientDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/clients")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void update_Should_Return_Status_OK() throws Exception
    {
        when(clientService.save(any(Client.class))).thenReturn(clients.stream().findFirst().get());

        ClientDto clientDto = clientMapper.toDto(clients.stream().findFirst().get());
        String expectedJson = new ObjectMapper().writeValueAsString(clientDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/clients/2")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_CLIENT")
    @Test
    public void update_Should_Return_Status_Bad_Request() throws Exception
    {
        when(clientService.save(any(Client.class))).thenReturn(clients.stream().findFirst().get());

        Client invalidClient = clients.stream().findFirst().get();
        invalidClient.setName("invalid");

        ClientDto clientDto = clientMapper.toDto(invalidClient);
        String expectedJson = new ObjectMapper().writeValueAsString(clientDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/clients/2")
                .accept(MediaType.APPLICATION_JSON)
                .content(expectedJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @WithMockUser(authorities = "ROLE_OPERATOR")
    @Test
    public void delete_Should_Return_Status_OK() throws Exception
    {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/clients/2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

}
