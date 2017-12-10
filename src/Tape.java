import java.util.ArrayList;
import java.util.List;

import static java.rmi.Naming.list;

/**
 * Created by Siedg on 24/11/2017.
 */
public class Tape {
    private ArrayList<String> tape;
    private int currentPosition;
    private String blankSymbol;
    private List<Transition> transitions;
    private String tapeContent;
    private int tapeNumber;
    private Transition actualTransition;

    public Tape(String tapeContent, String blankSymbol, int tapeNumber) {
        this.tapeContent = tapeContent;
        tape = new ArrayList<>();
        currentPosition = 0;
        this.blankSymbol = blankSymbol;
        this.tapeNumber = tapeNumber;
        transitions = new ArrayList<>();
        fillTape();
    }

    // Write symbol on current tape.
    public void write(String symbol, String move) {
        symbol = symbol.trim();
        move = move.trim();

        if (move.equals("L")) {
            if (currentPosition == 0) {
                tape.add(blankSymbol);
                reallocateTape(tape);
            }
            tape.set(currentPosition, symbol);
            currentPosition--;
        } else if (move.equals("R")) {
            if (currentPosition == tape.size()) {
                tape.add(blankSymbol);
            }

            tape.set(currentPosition, symbol);
            currentPosition++;
        } else if (move.equals("S")) {
            tape.set(currentPosition, symbol);
        }
    }

    private void reallocateTape(ArrayList<String> tape) {
        for (int i = tape.size() ; i > 0 ; i++) {
            tape.set(i, tape.get(i - 1));
        }
        tape.set(0, blankSymbol);
        currentPosition = currentPosition + 1;
    }

    public void printTape() {
        System.out.println("Posição atual: " + currentPosition);
        for (int i = 0 ; i < tape.size(); i++) {
            if (i == 0) {
                System.out.println("[" + tape.get(i));
            } else {
                System.out.println(", " + tape.get(i));
            }
        }
        System.out.println("]");
    }

    public void fillTape() {
        char c[] = tapeContent.toCharArray();
        for (char ch : c) {
            tape.add(Character.toString(ch));
        }
    }

    public void addTransition(Transition t) {
        transitions.add(t);
    }

    public ArrayList<String> getTape() {
        return tape;
    }

    public void setTape(ArrayList<String> tape) {
        this.tape = tape;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getBlankSymbol() {
        return blankSymbol;
    }

    public void setBlankSymbol(String blankSymbol) {
        this.blankSymbol = blankSymbol;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public String getTapeContent() {
        return tapeContent;
    }

    public void setTapeContent(String tapeContent) {
        this.tapeContent = tapeContent;
    }

    public int getTapeNumber() {
        return tapeNumber;
    }

    public void setTapeNumber(int tapeNumber) {
        this.tapeNumber = tapeNumber;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public Transition getActualTransition() {
        return actualTransition;
    }

    public void setActualTransition(Transition actualTransition) {
        this.actualTransition = actualTransition;
    }

}
