package Algoritmos;

import Distancias.*;
import Estructura.*;
import Exepciones.NotTrainedException;

import java.util.List;

public class KNearestNeighbours implements Algorithm<TableWithLabels, String, List<Double>>, DistanceClient {

    private TableWithLabels data;

    private Distance distance;

    public KNearestNeighbours( Distance distance ) {
        this.distance = distance;
    }

    public void train (TableWithLabels data) {
        this.data = data;
    }

    // Euclidean ecuation implemented in distanceBetweenRows (should be accessible)
    public String estimate (List<Double> sample) throws NotTrainedException {
        if (data == null) throw new NotTrainedException();
        RowWithLabels sampleRow = new RowWithLabels();
        for (Double value : sample) sampleRow.addItem(value);
        // Compare with every row
        Double minDist = -1.0;
        RowWithLabels minDistRow = sampleRow; // placeholder, and helps to find out if has been changed
             for (int i =0; i < data.size(); i++) {
                 RowWithLabels row = data.getRowAt(i);
                 Double distAct = distance.calculateDistance(row.getData(), sampleRow.getData());
                //Double distAct = row.distanceTo(sampleRow);
                //System.out.println(distAct);
                if (minDist < 0 || (distAct < minDist && distAct >= 0)) {
                    minDist = distAct;
                    minDistRow = row;
                    //System.out.println("found!");
                }
            }
        if (minDistRow != sampleRow) return minDistRow.getLabel();
        return "Not found";
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }

}
