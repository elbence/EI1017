import java.util.LinkedList;
import java.util.List;

public class Row {

    private List<Double> data;

    public Row(List<Double> datos){
        data = datos;
    }
    public Row() {
        data = new LinkedList<>();
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

    @Override
    public String toString() {
        return data.toString();
    }

    // recommended remove
    // recommended equals
    // more methods...

}
