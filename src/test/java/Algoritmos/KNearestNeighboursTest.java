package Algoritmos;

import Algoritmos.KNearestNeighbours;
import Distancias.*;
import Estructura.CSV;
import Estructura.RowWithLabels;
import Estructura.TableWithLabels;
import Exepciones.NotTrainedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KNearestNeighboursTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");

    @Test
    void estimate() {
        Factory distancia = new DistanceFactory();
        Distance eucdist = distancia.getDistance(DistanceType.EUCLIDEAN);
        KNearestNeighbours knn = new KNearestNeighbours(eucdist);
        knn.train(table);
        try {
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
        catch (NotTrainedException e){
            e.printStackTrace();
        }
        KNearestNeighbours knn2 = new KNearestNeighbours(eucdist);
        RowWithLabels row2 = table.getRowAt(0);
        assertThrows(NotTrainedException.class, () -> knn2.estimate(row2.getData()));
        knn2.train(table);
        assertDoesNotThrow(() -> knn2.estimate(row2.getData()));
    }



}