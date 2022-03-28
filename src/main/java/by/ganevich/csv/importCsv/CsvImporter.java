package by.ganevich.csv.importCsv;

import java.io.File;
import java.io.IOException;

public abstract class CsvImporter {

    public void doImport(String fileName) throws IOException {
        importCsv(fileName);
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("DELETE");
        } else System.out.println("NOT DELETE");
    }


    public abstract void importCsv(String fileName) throws IOException;

}
