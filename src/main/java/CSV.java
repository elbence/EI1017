import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSV {

    // Se tomará constructor por defecto
    // Se trata de una clase para gestionar ficheros del tipo CSV

    public Table readTable (String route) {
        try {
            File myFile = new File(route);
            Scanner myIter = new Scanner(myFile);
            myIter.useDelimiter(",");

            Table table = new Table();
            if (myIter.hasNextLine()) addHeadersToTable(myIter.nextLine(), table);
            while (myIter.hasNextLine()) {
                String line = myIter.nextLine();
                table.addRow(stringToRow(line));
            }

            return table;

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
            e.printStackTrace();
        }
        return null;
    }

    // Adds the strings in headerList as headers of the Table table
    private void addHeadersToTable (String headersList, Table table) {
        Scanner headerScanner = new Scanner(headersList);
        headerScanner.useDelimiter(",");
        while (headerScanner.hasNext()) {
            table.addHeader(headerScanner.next());
        }
    }

    // Converts a String into a Row and returns it (NO SAFETY, should be checked first!)
    private Row stringToRow (String linea) {
        Row newRow = new Row();
        Scanner lineScanner = new Scanner(linea);
        lineScanner.useDelimiter(",");
        while (lineScanner.hasNext()) newRow.addItem(lineScanner.nextDouble());
        return newRow;
    }

}