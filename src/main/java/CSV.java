import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSV {
    public Table readTable (String route) {

        try {
            File myFile = new File(route);
            Scanner myIter = new Scanner(myFile);
            while(myIter.hasNext()) {
                String data = myIter.next();
                System.out.println(data);
            }

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File does not exist.");
            e.printStackTrace();
        }

        return null;
    }
}