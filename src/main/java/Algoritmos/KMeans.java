package Algoritmos;

import Estructura.*;
import Exepciones.NoDataException;

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
            System.out.println(act);
            Representatives[i] = table.getRowAt(act);
            Representatives[i].addLabel("cluster-" + (i + 1));
            System.out.println(Representatives[i]);
        }

        // calculate distance of each element to every centroid
        // and assign proper cluster tag
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
                //System.out.println(element);System.out.println();
            }
        } catch (NoDataException e) {
            System.out.println("Missing data");
        }

        // recalculate centroids


        return;
    }

    @Override
    public String estimate(Row data) {
        return null;
    }
}
