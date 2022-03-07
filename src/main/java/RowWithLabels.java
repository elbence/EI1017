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
}
