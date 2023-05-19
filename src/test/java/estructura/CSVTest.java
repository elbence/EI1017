package estructura;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {

    private CSV gestorCSV = new CSV();

    private String answerForReadTable = "" +
            "[temperature, nonsense]\n" +
            "[32.0, 0.0]\n" +
            "[40.0, 104.0]\n" +
            "[70.0, 158.5]\n" +
            "[50.0, 122.0]\n" +
            "[37.7, 100.0]";

    private String answerForReadTableWithLabels = "" +
            "[color code, size, car]\n" +
            "[123.5, 365.3] toyota aigo\n" +
            "[234.6, 401.4] seat ibiza\n" +
            "[213.8, 550.3] nissan gtr\n" +
            "[415.2, 410.2] mini cooper";

    @Test
    void readTable() {
        Table table = gestorCSV.readTable("src/main/resources/temperatures.csv");
        assertEquals(answerForReadTable, table.toString());
    }

    @Test
    void readTableWithLabels() {
        TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/cars_with_names.csv");
        assertEquals(answerForReadTableWithLabels, table.toString());
    }
}