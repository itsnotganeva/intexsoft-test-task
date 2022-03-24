package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.CsvClientMapper;
import by.ganevich.entity.Client;
import by.ganevich.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientExporter extends CsvExporter {

    private final ClientService clientService;
    private final CsvClientMapper clientMapper;


    @Override
    public void exportCsv(String fileName) throws IOException {
        List<Client> clients = clientService.readClients();
        for (Client c : clients) {
        clientMapper.toCsv(fileName, c);
    }
    }
}
