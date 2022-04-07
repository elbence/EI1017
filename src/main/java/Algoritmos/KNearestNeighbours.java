package Algoritmos;

import Distancias.Distance;
import Distancias.DistanceFactory;
import Distancias.DistanceType;
import Distancias.Factory;
import Estructura.*;
import Exepciones.NotTrainedException;

import java.util.List;

public class KNearestNeighbours implements Algorithm<TableWithLabels, String, List<Double>>{

    private TableWithLabels data;

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
                 Factory distancia = new DistanceFactory();
                 Distance eucDist = distancia.getDistance(DistanceType.EUCLIDEAN);
                 Double distAct = eucDist.distanceTo(row, sampleRow);
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

}
