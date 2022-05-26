package Estructura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row {

    private List<Double> data;
    public Row() {
        data = new ArrayList<>();
    }

    public Row(List<Double> datos){
        data = datos;
    }

    public int size() {return data.size();}
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

    public int set (Double value, int index) {
        if (value != null && index < data.size() && index >= 0) {
            data.set(index, value);
            return 0;
        }
        return -1;
    }

    public Double get(int index) {return data.get(index);}

    @Override
    public String toString() {
        return data.toString();
    }

}
