package by.ganevich.csv.csvMapper;

import java.io.IOException;
import java.util.List;

public interface CsvMapper<T> {
    List<T> toEntity(String fileName) throws IOException;
    void toCsv(String fileName, T entity) throws IOException;
}
