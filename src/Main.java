import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siedg on 24/11/2017.
 */
public class Main {
    public static void main(String[] args) {
        boolean debug = false;
        String path = "";
        //String path = "Testes/3tape-automaton-sameABCnumber.txt";
        //String path = "Testes/ex9-2tape-substring.txt";
        //String path = "Testes/ex9.5fix-b.txt";
        TuringMachine tm;


        List<String> tapesContent = new ArrayList<>();
        //tapesContent.add("ab");

        if (args.length >= 2) {
            try {
                path = args[0];
                for (int i = 1 ; i <= args.length ; i++) {
                    tapesContent.add(args[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("MODO DE USO: java -jar mt.txt \"conteúdo fita 1\" [\"conteúdo fita 2\"] ... [\"conteúdo fita N\"]");
            System.exit(0);
        }

        List<String> fileContents = new File().readFile(path);
        tm = new TuringMachine(fileContents, tapesContent, debug);


    }
}
