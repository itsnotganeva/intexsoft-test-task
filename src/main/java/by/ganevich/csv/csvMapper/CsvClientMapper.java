package by.ganevich.csv.csvMapper;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import by.ganevich.csv.CsvWriter;
import by.ganevich.entity.Client;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvClientMapper implements CsvMapper<Client> {
    @Override
    public List<Client> toEntity(String fileName) throws IOException {
        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        List<Client> clients = new ArrayList<>();
        List list = csv.parse(setColumnMapping(), csvReader);
        for (Object object : list) {
            Client client = (Client) object;;
            clients.add(client);
        }
        csvReader.close();
        return clients;
    }

    public void toCsv(String fileName, Client client) throws IOException {
        String toCsv = client.getId().toString() + "," + client.getName() + "," + client.getType().toString();
        CsvWriter csvWriter = new CsvWriter();
        csvWriter.writeCsv(fileName, toCsv);
    }

    private static ColumnPositionMappingStrategy setColumnMapping()
    {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Client.class);
        String[] columns = new String[] {"name", "type"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
