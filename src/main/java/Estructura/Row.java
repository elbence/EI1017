package Estructura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row {

    private List<Double> data;

    public Row(List<Double> datos){
        data = datos;
    }
    public Row() {
        data = new ArrayList<>();
    }

    public List<Double> getData () {
        return data;
    }

    // ret 0 if succes; -1 if fail
    public int addItem (Double item) {
        if (item != null) {
            data.add(item);
            return 0;
        }
        return -1;
    }

    // compares parsed ROW to the row from which is called
    // returns an abs value for expresing distance or -1 if rows are not comparable
    public Double distanceTo(Row row) {
        if (row.getData().size() == data.size()) {
            Iterator<Double> iter1 = row.getData().iterator();
            Iterator<Double> iter2 = data.iterator();
            Double dist = 0.0;
            while (iter1.hasNext()) dist += Math.pow(iter1.next() - iter2.next(), 2);
            dist = Math.pow(dist, 0.5);
            return dist;
        }
        return -1.0;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    // recommended remove
    // recommended equals
    // more methods...

}
