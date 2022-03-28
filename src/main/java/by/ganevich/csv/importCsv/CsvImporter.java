package by.ganevich.csv.importCsv;

import java.io.File;
import java.io.IOException;

public abstract class CsvImporter {

    public void doImport(String fileName) throws IOException {
        importCsv(fileName);
        File file = new File(fileName);
        file.delete();
    }


    public abstract void importCsv(String fileName) throws IOException;

}
