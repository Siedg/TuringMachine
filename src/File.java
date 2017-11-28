import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siedg on 24/11/2017.
 */


public class File {

    // Read file and return a String with its contents.
    public List<String> readFile(String path) {
        BufferedReader reader;
        List<String> fileString = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                fileString.add(line);
                line = reader.readLine();
                //fileString.add(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileString;
    }

    // Puts the contents of a String in a file.
    public void writeFile(String path, String content) {
        try {
            FileWriter file = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(content);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
