import Algoritmos.KNearestNeighbours;
import Estructura.CSV;
import Estructura.RowWithLabels;
import Estructura.TableWithLabels;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KNearestNeighboursTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");

    @Test
    void estimate() {
        KNearestNeighbours knn = new KNearestNeighbours();
        knn.train(table);

        RowWithLabels rowAct = table.getRowAt(0);
        String guessedLabel = knn.estimate(rowAct.getData());
        System.out.println("Expected: " + rowAct.getLabel());
        System.out.println("Actual: " + guessedLabel);
        assertEquals(rowAct.getLabel(), guessedLabel);
        System.out.println();

        rowAct = table.getRowAt(4);
        guessedLabel = knn.estimate(rowAct.getData());
        System.out.println("Expected: " + rowAct.getLabel());
        System.out.println("Actual: " + guessedLabel);
        assertEquals(rowAct.getLabel(), guessedLabel);
        System.out.println();

        rowAct = table.getRowAt(55);
        guessedLabel = knn.estimate(rowAct.getData());
        System.out.println("Expected: " + rowAct.getLabel());
        System.out.println("Actual: " + guessedLabel);
        assertEquals(rowAct.getLabel(), guessedLabel);
        System.out.println();

        rowAct = table.getRowAt(126);
        guessedLabel = knn.estimate(rowAct.getData());
        System.out.println("Expected: " + rowAct.getLabel());
        System.out.println("Actual: " + guessedLabel);
        assertEquals(rowAct.getLabel(), guessedLabel);

    }
}