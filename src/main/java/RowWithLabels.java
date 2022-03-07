public class RowWithLabels extends Row{
    private String label;
    public RowWithLabels(){
        super();
        label = null;
    }

    public String getLabel() {
        return label;
    }

    public void addLabel (String label) {this.label = label;}
    public int addItem (Double item) { return super.addItem(item);}

    @Override
    public String toString() {
        return super.toString() +" "+ label;
    }
}
