package algoritmos;

import distancias.*;
import estructura.CSV;
import estructura.TableWithLabels;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");
    Factory distancia = new DistanceFactory();
    Distance eucdist = distancia.getDistance(DistanceType.EUCLIDEAN);

    @Test
    void train() {

        KMeans kmeans;
        kmeans = new KMeans(1,1, 1, eucdist);
        kmeans.train(table);
        assertEquals(Arrays.toString(kmeans.getRepresentatives()), "[[5.843333333333335, 3.0540000000000007, 3.7586666666666693, 1.1986666666666672] cluster-1]");

        TableWithLabels table2 = gestorCSV.readTableWithLabels("src/main/resources/easy_kmeans.csv");
        kmeans = new KMeans(1,2, 2, eucdist);
        kmeans.train(table2);
        assertEquals(Arrays.toString(kmeans.getRepresentatives()), "[[45.0, 20.0] cluster-1]");

    }

    @Test
    void estimate() {

        KMeans kmeans;
        String guess;

        kmeans = new KMeans(3,5, 234521, eucdist);
        kmeans.train(table);
        guess = kmeans.estimate(table.getRowAt(15));
        assertEquals("cluster-1", guess);
        System.out.println();

        kmeans = new KMeans(3,10, 234521, eucdist);
        kmeans.train(table);
        guess = kmeans.estimate(table.getRowAt(132));
        assertEquals("cluster-2", guess);
        System.out.println();

    }
}