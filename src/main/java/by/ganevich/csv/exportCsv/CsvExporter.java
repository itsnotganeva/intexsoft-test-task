package by.ganevich.csv.exportCsv;

import by.ganevich.csv.csvMapper.BaseCsvMapper;
import by.ganevich.service.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.List;


public abstract class CsvExporter<T> {

    public abstract String getFileName();

    public abstract BaseService getService();

    public abstract BaseCsvMapper getMapper();

    public File exportCsv() throws IOException {
        File file = new File(getFileName());
        file.delete();
        doExport(getFileName());
        File exportedFile = new File(getFileName());
        return exportedFile;
    }

    public void doExport(String fileName) throws IOException {
        List<T> entities = getService().readAll();
        for (T entity : entities) {
            getMapper().toCsv(fileName, entity);
        }
    }
}
