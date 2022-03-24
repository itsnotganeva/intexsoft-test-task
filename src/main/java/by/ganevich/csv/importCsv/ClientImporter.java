package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.CsvClientMapper;
import by.ganevich.entity.Client;
import by.ganevich.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientImporter extends CsvImporter {

    private final ClientService clientService;
    private final CsvClientMapper clientMapper;

    @Override
    public void importCsv(String fileName) throws FileNotFoundException {
        List<Client> clients = clientMapper.toEntity(fileName);
        for (Client c : clients) {
            clientService.saveClient(c);
        }
    }
}
