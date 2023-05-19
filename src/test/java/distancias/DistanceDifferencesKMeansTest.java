package distancias;

import algoritmos.KMeans;
import estructura.CSV;
import estructura.TableWithLabels;
import org.junit.jupiter.api.Test;

public class DistanceDifferencesKMeansTest {

    private CSV gestorCSV = new CSV();
    private TableWithLabels table = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");

    @Test
    void estimate() {
        Factory distancia = new DistanceFactory();
        Distance manDist = distancia.getDistance(DistanceType.MANHATTAN);
        int maxIterations = 6; // recommended 5 - 10
        int numberClusters = 10; // more means more exception probability
        int trainIterations = 20; // results may vary, high could tend to more exceptions

        KMeans kmeans;
        String[] EUCguess = new String[5];
        String[] MANguess = new String[5];
        int[] randomIndexes = new int[5];
        int exceptionsFound = 0;

        System.out.println("CURRENTLY:");
        System.out.println(" * Total Clusters ---- " + numberClusters);
        System.out.println(" * Train Iterations -- " + trainIterations);
        System.out.println(" * Max. Tries -------- " + maxIterations);
        System.out.println();

        for (int o = 0; o < maxIterations; o++) { // repeat maxIterations times to search

            System.out.println(" --> BEGIN EXCEPTION SEARCH n" + o);
            System.out.println();

            for (int i = 0; i < 5; i++) randomIndexes[i] = (int) Math.round(Math.random() * (table.size()-1));

            kmeans = new KMeans(numberClusters, trainIterations, 234521, new EuclideanDistance());
            kmeans.train(table);

            for (int i = 0; i < 5; i++)
                EUCguess[i] = kmeans.estimate(table.getRowAt(randomIndexes[i]));

            kmeans.setDistance(manDist);
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

        if (exceptionsFound == 0) System.out.println("If no exceptions were found try raising maxIterations value or just trying again the test, there are some variables that could be modified if wanted too...");

    }

}
