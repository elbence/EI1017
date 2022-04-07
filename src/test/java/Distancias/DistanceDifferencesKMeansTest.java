package Distancias;

import Algoritmos.KMeans;
import Estructura.CSV;
import Estructura.TableWithLabels;
import org.junit.jupiter.api.Test;

public class DistanceDifferencesKMeansTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");

    @Test
    void estimate() {

        KMeans kmeans;
        String[] EUCguess = new String[5];
        String[] MANguess = new String[5];
        int[] randomIndexes = new int[5];
        int exceptionsFound = 0;

        int maxIterations = 6; // recommended 5 - 10

        for (int o = 0; o < maxIterations; o++) { // repeat maxIterations times to search

            System.out.println(" --> BEGIN EXCEPTION SEARCH n" + o);
            System.out.println();

            for (int i = 0; i < 5; i++) randomIndexes[i] = (int) Math.round(Math.random() * (table.size()-1));

            kmeans = new KMeans(6, 1, 234521, new EuclideanDistance());
            kmeans.train(table);

            for (int i = 0; i < 5; i++)
                EUCguess[i] = kmeans.estimate(table.getRowAt(randomIndexes[i]));

            kmeans.setDistance(new ManhattanDistance());
            kmeans.train(table);

            for (int i = 0; i < 5; i++)
                MANguess[i] = kmeans.estimate(table.getRowAt(randomIndexes[i]));


            for (int i = 0; i < 5; i++) {
                String def = "";
                if (!MANguess[i].equals(EUCguess[i])) {
                    def = " * ";
                    exceptionsFound++;
                }
                System.out.println("USED ROW: " + randomIndexes[i] + " - DATA:" + table.getRowAt(randomIndexes[i]));
                System.out.println(def + "EUC Guess: " + EUCguess[i]);
                System.out.println(def + "MAN Guess: " + MANguess[i]);
                System.out.println();
            }

            if (exceptionsFound == 0)
                System.out.println("NO EXCEPTIONS FOUND, prepare next round...\n\n");
            else {
                System.out.println("" + exceptionsFound + " EXCEPTIONS FOUND, end demonstation...");
                o += maxIterations;
            }

        }

        if (exceptionsFound == 0) System.out.println("If no exceptions were found try raising maxIterations value or just trying again the test");

    }

}
