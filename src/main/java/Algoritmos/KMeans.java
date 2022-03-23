package Algoritmos;

import Estructura.*;
import Exepciones.NoDataException;
import Exepciones.NotTrainedException;

import java.lang.ref.SoftReference;
import java.util.Arrays;

public class KMeans implements  Algorithm<Table, String, Row>{

    private int numberClusters;
    private int iterations;
    private long seed;

    private RowWithLabels[] Representatives;
    private TableWithLabels table;

    public KMeans(int numberClusters, int iterations, long seed) {
        this.numberClusters = numberClusters;
        this.iterations = iterations;
        this.seed = seed;
        Representatives = new RowWithLabels[numberClusters];
    }

    @Override
    public void train(Table data) {
        // choose representatives
        table = (TableWithLabels) data;

        for (int i = 0; i < numberClusters; i++) {
            int act = (int) (seed + i * seed % 1000) % table.size();
            //System.out.println(act);
            Representatives[i] = table.getRowAt(act);
            Representatives[i].addLabel("cluster-" + (i + 1));
            //System.out.println(Representatives[i]);
        }

        for (int iters = 0; iters < iterations; iters++) {

            // calculate distance of each element to every centroid
            // and assign proper cluster tag
            // parallel: count how many elements are there for each cluster
            int[] elementsOnCluster = new int[numberClusters];
            for (int i = 0; i < numberClusters; i++) elementsOnCluster[i] = 0;

            try {
                for (RowWithLabels element : table.getAllData()) {
                    int elementCluster = 0;
                    Double minDist = -1.0;
                    Double distAct;
                    int i = 0;
                    for (RowWithLabels representative : Representatives) {
                        i++;
                        distAct = element.distanceTo(representative);
                        //System.out.println(distAct);
                        if (distAct < minDist || minDist < 0) {
                            minDist = distAct;
                            elementCluster = i;
                            //System.out.println("new min ^^^");
                        }
                    }
                    element.addLabel("cluster-" + elementCluster);
                    elementsOnCluster[elementCluster - 1]++;
                    //System.out.println(element);System.out.println();
                }
            } catch (NoDataException e) {
                //System.out.println("Missing data");
            }
            //System.out.println(Arrays.toString(elementsOnCluster));

            // recalculate centroids
            // redeclare Representatives and prepare for operations
            int regRowSize = Representatives[0].size();
            for (int i = 0; i < numberClusters; i++) {
                Representatives[i] = new RowWithLabels();
                Representatives[i].addLabel("cluster-" + (i + 1));
                for (int o = 0; o < regRowSize; o++) Representatives[i].addItem(0.0);
            }
            //System.out.println(Arrays.toString(Representatives));
            try {
                // add all data to respective representative
                for (RowWithLabels element : table.getAllData()) {
                    int clustNum = extractCluster(element.getLabel());
                    for (int i = 0; i < regRowSize; i++) {
                        Double newData = Representatives[clustNum - 1].get(i) + element.get(i);
                        Representatives[clustNum - 1].set(newData, i);
                    }
                }
                // mean data to the number of members it had
                for (int i = 0; i < numberClusters; i++) {
                    for (int o = 0; o < regRowSize; o++) {
                        Double fixedData = Representatives[i].get(o) / elementsOnCluster[i];
                        Representatives[i].set(fixedData, o);
                    }
                }
            } catch (NoDataException e) {
                //System.out.println("Missing data");
            }
            //System.out.println(Arrays.toString(Representatives));
        }
        // Finished training!
    }

    private int extractCluster(String label) {
        String num = label.replaceAll("[^0-9]","");
        return Integer.parseInt(num);
    }

    @Override
    public String estimate(Row data) {

        TableWithLabels repTable = new TableWithLabels();
        for (RowWithLabels row : Representatives) repTable.addRow(row);

        KNearestNeighbours knn = new KNearestNeighbours();
        knn.train(repTable);

        try {
            String guessedLabel = knn.estimate(data.getData());
            return guessedLabel;
        } catch (NotTrainedException e) {
            return "Not trained to guess";
        }
    }
}
