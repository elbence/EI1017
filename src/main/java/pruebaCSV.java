public class pruebaCSV {
    public static void main(String[] args) {
        CSV gestorCSV = new CSV();
        Table tmp = gestorCSV.readTable("src/main/resources/miles_dollars.csv");
        System.out.println(tmp);
        for (int i = 0; i < 20; i++) System.out.println();
        Table tmpWL = gestorCSV.readTableWithLabels("src/main/resources/iris.csv");
        //System.out.println(tmpWL);
    }
}
