package Algoritmos;

import Estructura.RowWithLabels;
import Estructura.TableWithLabels;

import java.util.List;

public class KNearestNeighbours implements Algorithm<TableWithLabels, String, List<Double>>{

    private TableWithLabels data;

    public void train (TableWithLabels data) {
        this.data = data;
    }

    // Euclidean ecuation implemented in distanceBetweenRows (should be accessible)
    public String estimate (List<Double> sample) {
        RowWithLabels sampleRow = new RowWithLabels();
        for (Double value : sample) sampleRow.addItem(value);
        // Compare with every row
        Double minDist = -1.0;
        RowWithLabels minDistRow = sampleRow; // placeholder, and helps to find out if has been changed
        for (RowWithLabels row : data.getAllData()) {
            Double distAct = row.distanceBetweenRows(sampleRow);
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