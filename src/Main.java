import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Siedg on 24/11/2017.
 */
public class Main {
    public static void main(String[] args) {
        boolean debug = true;
        //String path = "Testes/3tape-automaton-sameABCnumber.txt";
        //String path = "Testes/ex9-2tape-substring.txt";
        String path = "Testes/ex9.5fix-b.txt";
        List<String> fileContents = new File().readFile(path);
        TuringMachine tm;
        Scanner scanner = new Scanner(System.in);




        List<String> tapesContent = new ArrayList<>();
        tapesContent.add("ab");



        tm = new TuringMachine(fileContents, tapesContent, debug);
        /*
        if (args.length >= 2) {
            try {
                path = args[0];
                for (int i = 1 ; i <= args.length ; i++) {
                    tapesContent.add(args[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */

/*
        if (debug) {
            //System.out.println(fileContents);

            int i = 0;
            for (String s : fileContents) {
                System.out.println(i + "  ==  " + s);
                i++;
            }



        }
        String input = "";
        while (!input.equals("exit")) {
            input = scanner.nextLine();
            tm = new TuringMachine(fileContents, tapesContent, debug);
        }
*/

    }
}
