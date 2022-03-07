import java.util.Iterator;

public class RowWithLabels extends Row{
    private String label;
    public RowWithLabels(String label){
        super();
        this.label = label;
    }
    public RowWithLabels(){
        super();
        label = null;
    }

    public String getLabel() {
        return label;
    }

    public void addLabel (String label) {this.label = label;}

    public Double distanceBetweenRows(RowWithLabels row) {
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
        return super.toString() +" "+ label;
    }
}
