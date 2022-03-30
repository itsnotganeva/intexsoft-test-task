package by.ganevich.csv.importCsv;

import by.ganevich.csv.csvMapper.BaseCsvMapper;
import by.ganevich.service.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class CsvImporter<T> {

    public abstract BaseService getService();

    public abstract BaseCsvMapper getMapper();

    public void importCsv(String fileName) throws IOException {
        doImport(fileName);
        File file = new File(fileName);
        file.delete();
    }

    public void doImport(String fileName) throws IOException {
        List<T> entities = getMapper()
                .toEntity(fileName);
        for (T entity : entities) {
            getService().save(entity);
        }
    }

}
