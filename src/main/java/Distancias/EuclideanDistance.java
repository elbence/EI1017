package Distancias;

import Estructura.Row;

import java.util.Iterator;

public class EuclideanDistance implements Distance{
    @Override
    public Double distanceTo(Row row1, Row row2) {
        if (row2.getData().size() == row1.size()) {
            Iterator<Double> iter1 = row2.getData().iterator();
            Iterator<Double> iter2 = row1.getData().iterator();
            Double dist = 0.0;
            while (iter1.hasNext()) dist += Math.pow(iter1.next() - iter2.next(), 2);
            dist = Math.pow(dist, 0.5);
            return dist;
        }
        return -1.0;
    }
}
