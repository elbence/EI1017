package algoritmos;

import distancias.*;
import estructura.*;
import exepciones.NotTrainedException;

public class KMeans implements  Algorithm<Table, String, Row>, DistanceClient {

    private int numberClusters;
    private int iterations;
    private long seed;

    private RowWithLabels[] representatives;
    private TableWithLabels table;

    private int[] elementsOnCluster;

    private Distance distance;
    private static final String CLUSTER_PREFIX = "cluster-";

    public KMeans(int numberClusters, int iterations, long seed, Distance distance) {
        this.numberClusters = numberClusters;
        elementsOnCluster = new int[numberClusters];
        restartClusterElementsCounter();
        this.iterations = iterations;
        this.seed = seed;
        representatives = new RowWithLabels[numberClusters];
        this.distance = distance;
    }

    @Override
    public void train(Table data) {
        if (data == null || data.isEmpty()) return; //deal with empty data
        table = (TableWithLabels) data; // save given data
        chooseRepresentatives(); // choose representatives from data

        for (int current_iteration = 0; current_iteration < iterations; current_iteration++) { // repeat rest of steps
            restartClusterElementsCounter(); // restart vector for counting elements on clusters

            // calculate the cluster of each element & assign tag + count how many elements per cluster there are
            for (int j =0; j < table.size(); j++) {
                RowWithLabels element = table.getRowAt(j);
                int elementCluster = calculateElementCluster(element);
                assignClusterTag(element, elementCluster);
                increaseClusterMembers(elementCluster);
            }
            recalculateRepresentatives(); // recalculate centroids
        }
        // Finished training!
    }

    @Override
    public String estimate(Row data) {

        TableWithLabels repTable = new TableWithLabels();
        for (RowWithLabels row : representatives) repTable.addRow(row);

        KNearestNeighbours knn = new KNearestNeighbours(distance);
        knn.train(repTable);

        try {
            return knn.estimate(data.getData());
        } catch (NotTrainedException e) {
            return "Not trained to guess";
        }
    }

    private void chooseRepresentatives() {
        for (int i = 0; i < numberClusters; i++) {
            int act = (int) (seed + i * seed % 1000) % this.table.size();
            representatives[i] = this.table.getRowAt(act);
            representatives[i].addLabel(CLUSTER_PREFIX + (i + 1));
        }
    }

    private int calculateElementCluster(RowWithLabels element) {
        int elementCluster = 0;
        Double minDist = -1.0;
        Double distAct;
        int i = 0;
        for (RowWithLabels representative : representatives) {
            i++;
            distAct = distance.calculateDistance(element.getData(), representative.getData());
            if (distAct < minDist || minDist < 0) {
                minDist = distAct;
                elementCluster = i;
            }
        }
        return elementCluster;
    }

    private void restartClusterElementsCounter() {
        for (int i = 0; i < numberClusters; i++) elementsOnCluster[i] = 0;
    }

    private void assignClusterTag(RowWithLabels element, int elementCluster) {
        element.addLabel(CLUSTER_PREFIX + elementCluster);
    }

    private void increaseClusterMembers(int elementCluster) {
        elementsOnCluster[elementCluster - 1]++;
    }

    private void recalculateRepresentatives() {
        // redeclare Representatives and prepare for operations
        int regRowSize = representatives[0].size();
        for (int i = 0; i < numberClusters; i++) {
            representatives[i] = new RowWithLabels();
            representatives[i].addLabel(CLUSTER_PREFIX + (i + 1));
            for (int o = 0; o < regRowSize; o++) representatives[i].addItem(0.0);
        }

        // add all data to respective representative
        for (int j =0; j < table.size(); j++) {
            RowWithLabels element = table.getRowAt(j);
            int clustNum = extractCluster(element.getLabel());
            for (int i = 0; i < regRowSize; i++) {
                Double newData = representatives[clustNum - 1].get(i) + element.get(i);
                representatives[clustNum - 1].set(newData, i);
            }
        }
        // mean data to the number of members it had
        for (int i = 0; i < numberClusters; i++) {
            for (int o = 0; o < regRowSize; o++) {
                Double fixedData = representatives[i].get(o) / elementsOnCluster[i];
                representatives[i].set(fixedData, o);
            }
        }

    }

    private int extractCluster(String label) {
        String num = label.replaceAll("\\D","");
        return Integer.parseInt(num);
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public RowWithLabels[] getRepresentatives() {
        return representatives;
    }

}
