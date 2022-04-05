package Algoritmos;

import Estructura.CSV;
import Estructura.TableWithLabels;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");

    @Test
    void train() {

        KMeans kmeans;

        kmeans = new KMeans(3,5, 234521);
        kmeans.train(table);
        System.out.println();

        kmeans = new KMeans(3,5, 452345);
        kmeans.train(table);
        System.out.println();

        kmeans = new KMeans(3,5, 1234455234);
        kmeans.train(table);
        System.out.println();

    }

    @Test
    void estimate() {

        KMeans kmeans;
        String guess;

        kmeans = new KMeans(3,5, 234521);
        kmeans.train(table);
        guess = kmeans.estimate(table.getRowAt(15));
        assertEquals("cluster-1", guess);
        System.out.println();

        kmeans = new KMeans(3,10, 234521);
        kmeans.train(table);
        guess = kmeans.estimate(table.getRowAt(132));
        assertEquals("cluster-2", guess);
        System.out.println();

    }
}