package by.ganevich.csv.csvMapper;

import by.ganevich.entity.Bank;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CsvMapper<T> {
    List<T> toEntity(String fileName) throws FileNotFoundException;
}
