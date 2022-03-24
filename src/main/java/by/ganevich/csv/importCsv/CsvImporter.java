package by.ganevich.csv.importCsv;

import java.io.FileNotFoundException;

public abstract class CsvImporter {

    public abstract void importCsv(String fileName) throws FileNotFoundException;

}
