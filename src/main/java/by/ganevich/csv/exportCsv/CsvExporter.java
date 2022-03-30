package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.BaseCsvMapper;
import by.ganevich.service.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.List;


public abstract class CsvExporter<T> {

    public abstract BaseService getService();

    public abstract BaseCsvMapper getMapper();

    public File exportCsv(String fileName) throws IOException {
        File file = new File(fileName);
        file.delete();
        doExport(fileName);
        File exportedFile = new File(fileName);
        return exportedFile;
    }

    public void doExport(String fileName) throws IOException {
        List<T> entities = getService().readAll();
        for (T entity : entities) {
            getMapper().toCsv(fileName, entity);
        }
    }
}
