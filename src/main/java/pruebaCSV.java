public class pruebaCSV {
    public static void main(String[] args) {
        CSV gestorCSV = new CSV();
        Table tmp = gestorCSV.readTable("src/main/resources/miles_dollars.csv");
        System.out.println(tmp);
    }
}
