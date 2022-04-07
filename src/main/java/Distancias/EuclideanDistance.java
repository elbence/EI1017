package Distancias;

import Estructura.Row;

import java.util.Iterator;
import java.util.List;

public class EuclideanDistance implements Distance{

    @Override
    public Double calculateDistance(List<Double> p, List<Double> q) {
        if (p.size() == q.size()) {
            Iterator<Double> iter1 = p.iterator();
            Iterator<Double> iter2 = q.iterator();
            Double dist = 0.0;
            while (iter1.hasNext()) dist += Math.pow(iter1.next() - iter2.next(), 2);
            dist = Math.pow(dist, 0.5);
            return dist;
        }
        return -1.0;
    }

}
