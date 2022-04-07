package Distancias;

import java.util.Iterator;
import java.util.List;

public class ManhattanDistance implements Distance{
    @Override
    public Double calculateDistance(List<Double> p, List<Double> q) {
        if (p.size() == q.size()) {
            Iterator<Double> iter1 = p.iterator();
            Iterator<Double> iter2 = q.iterator();
            Double dist = 0.0;
            while (iter1.hasNext()) dist += Math.abs(iter1.next() - iter2.next());
            return dist;
        }
        return -1.0;
    }
}
