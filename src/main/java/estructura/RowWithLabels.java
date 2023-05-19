package estructura;

public class RowWithLabels extends Row{
    private String label;
    
    public RowWithLabels(){
        super();
        label = null;
    }
    public RowWithLabels(String label){
        super();
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void addLabel (String label) {this.label = label;}

    @Override
    public String toString() {
        return super.toString() +" "+ label;
    }
}
