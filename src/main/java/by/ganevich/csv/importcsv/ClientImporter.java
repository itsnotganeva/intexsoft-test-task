package by.ganevich.csv.importcsv;

import by.ganevich.csv.csvmapper.BaseCsvMapper;
import by.ganevich.csv.csvmapper.CsvClientCsvMapper;
import by.ganevich.service.BaseService;
import by.ganevich.service.ClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ClientImporter extends CsvImporter {

    private final String fileName = "importClients.csv";
    private final ClientService clientService;
    private final CsvClientCsvMapper clientMapper;

    @Override
    public BaseService getService() {
        return this.clientService;
    }

    @Override
    public BaseCsvMapper getMapper() {
        return this.clientMapper;
    }
}
