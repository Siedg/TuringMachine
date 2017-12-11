import javafx.collections.transformation.TransformationList;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Siedg on 24/11/2017.
 */
public class TuringMachine {
    private List<State> initialState;
    private List<State> goalStates;
    private List<State> states;
    private List<String> inputAlphabet;
    private List<String> tapeAlphabet;
    private List<Transition> transitions;
    private String blankSymbol;
    private int numberOfTapes;
    private List<Tape> tapes;
    private List<String> inputTapeContent;
    private List<Boolean> canDoTransition = new ArrayList<>();
    private int transitionNumber = 0;
    public boolean debug;


    public TuringMachine(List<String> contents, List<String> inputTapeContent, boolean debug) {
        this.debug = debug;
        this.inputTapeContent = inputTapeContent;
        loadMachine(contents);

        for (String input : inputTapeContent) {
            if (!validateInput(input)) {
                System.out.println("ERRO: Existe algum símbolo na fita não presente em seu alfabeto!");
                System.exit(0);
            }
            else {
                System.out.println("Símbolos da fita válidos");
            }
        }

        if (debug) {
            System.out.println(getInitialState().get(0).getState());
        }
        List<State> endStates = runTM(getInitialState().get(0));
        boolean foundGoalState = false;
        for (State s : endStates) {
            if (isGoalState(s.getState())) {
                System.out.println("Palavra aceita!");
                System.out.println("Estado atual: " + s.getState());
                foundGoalState = true;
            }
        }

        if (!foundGoalState) {
            System.out.println("Palavra não aceita!");
            System.out.print("Estados atuais: ");
            for (State s : endStates) {
                System.out.print(s.getState());
                System.out.print(" ");
            }
            System.out.println("");
        }

        System.out.println("Fitas: ");
        for (Tape t : getTapes()) {
            System.out.println("Fita [" + t.getTapeNumber() + "]:");
            t.printTape();
        }
    }

    private void loadMachine(List<String> contents) {

        // Line 1 Input Alphabet
        inputAlphabet = new ArrayList<>();
        String line1[] = contents.get(0).split(" ");
        if (!Collections.addAll(inputAlphabet, line1)) {
            System.out.println("ERRO: Alfabeto de entrada incorreto!");
        }

        if (debug) {
            System.out.println("ALFABETO DE ENTRADA OK!");
        }

        // Line 2 Tape Alphabet
        tapeAlphabet = new ArrayList<>();
        String line2[] = contents.get(1).split(" ");
        if (!Collections.addAll(tapeAlphabet, line2)) {
            System.out.println("ERRO: Alfabeto da fita incorreto!");
        }

        if (debug) {
            System.out.println("ALFABETO DE DA FITA OK!");
        }

        // Line 3 Blank Symbol
        blankSymbol = contents.get(2);

        if (debug) {
            System.out.println("SÍMBOLO BRANCO OK!");
        }

        // Line 5 Initial State
        initialState = new ArrayList<>();
        String line5[] = contents.get(4).split(" ");
        for (String s : line5) {
            initialState.add(new State(s, true, false));
        }

        if (debug) {
            System.out.println(initialState.get(0).getState());
            System.out.println("ESTADO INICIAL OK!");
        }

        // Line 6 Final States
        goalStates = new ArrayList<>();
        String line6[] = contents.get(5).split(" ");
        for (String s : line6) {
            goalStates.add(new State(s, false, true));
        }

        if (debug) {
            System.out.println(goalStates.get(0).getState());
            System.out.println("ESTADOS FINAIS OK!");
        }

        // Line 4 States
        states = new ArrayList<>();
        String line4[] = contents.get(3).split(" ");
        for (String s : line4) {
            states.add(new State(s, isInitialState(s), isGoalState(s)));
        }

        if (debug) {
            System.out.println("LISTA DE ESTADOS OK!");
        }

        // Line 7 Number of Tapes
        numberOfTapes = Integer.parseInt(contents.get(6));
        tapes = new ArrayList<>();

        if (debug) {
            System.out.println("numberOfTapes: " + numberOfTapes);
        }

        for (int i = 0 ; i < numberOfTapes ; i++) {
            if (debug) {
                System.out.println("Creating tape: " + i);
            }
            tapes.add(new Tape(inputTapeContent.get(i), blankSymbol, i));
        }

        // Line 8-EOF Transitions
        transitions = new ArrayList<>();

        if (debug) {
            System.out.println("Start Transitions");
        }

        for (int i = 7 ; i < contents.size() ; i++) {
            String line[] = contents.get(i).split(" ");

            if (debug) {
                System.out.println("Transitions for: " + i);
                System.out.println("line.length: " + line.length);
                for (String s : line) {
                    //System.out.println(s);
                }
            }

            State originState = new State(line[0], isInitialState(line[0]), isGoalState(line[0]));
            State destinyState = new State(line[1], isInitialState(line[0]), isGoalState(line[0]));

            if (debug) {
                System.out.println(inputTapeContent);
                System.out.println("tapes.size(): " + tapes.size());
            }

            int j = 2;
            int currentTape = 0;
            String transition = "";
            while (j < line.length) {

                if (debug) {
                    System.out.println("==========");
                    System.out.println("While: " + j);
                    System.out.println("Fita: " + currentTape);
                }

                String oldSymbol = line[j];
                j++;
                String newSymbol = line[j];
                j++;
                String move = line[j];
                j++;



                //tapes.add(new Tape(inputTapeContent.get(currentTape), blankSymbol, currentTape));

                if (debug) {
                    System.out.println("originState: " + originState.getState());
                    System.out.println("destinyState: " + destinyState.getState());
                    System.out.println("oldSymbol: " + oldSymbol);
                    System.out.println("newSymbol: " + newSymbol);
                    System.out.println("move: " + move);
                    System.out.println("currentTape: " + currentTape);
                }

                tapes.get(currentTape).addTransition(new Transition(originState, destinyState, oldSymbol, newSymbol, move, currentTape, transitionNumber));
                currentTape++;
            }

            if (debug) {
                System.out.println("Transition: " + transition);
            }
            transitionNumber++;
        }

        if (debug) {
            System.out.println("End Transitions");

            System.out.println("===");
            for (Tape tapu : tapes) {
                System.out.println("tape: " + tapu.getTapeNumber());
                for (Transition transition : tapu.getTransitions()) {
                    System.out.println("oldSymbol: " + transition.getOldSymbol());
                    System.out.println("newSymbol: " + transition.getNewSymbol());
                    System.out.println("originState: " + transition.getOriginState().getState());
                    System.out.println("destinyState: " + transition.getDestinyState().getState());
                    System.out.println("move: " + transition.getMove());
                    System.out.println("===");
                }
            }
        }

        for (State state : states) {
            for (Tape tape : tapes) {
                for (Transition trans : tape.getTransitions()) {
                    if (state.getState().equals(trans.getOriginState().getState())) {
                        if (!state.getTransitions().contains(trans)) {
                            state.addTransition(trans);
                        }
                    }
                }
            }
        }

        if (debug) {
            System.out.println("=============================");
            System.out.println("states transitions");
            for (State state : states) {
                System.out.println("State: " + state.getState());
                System.out.println("transitionsNumber: " + state.getTransitions().size());
                for (Transition transition : state.getTransitions()) {
                    System.out.println("tapeNumber: " + transition.getTapeNumber());
                    System.out.println("oldSymbol: " + transition.getOldSymbol());
                    System.out.println("newSymbol: " + transition.getNewSymbol());
                    System.out.println("originState: " + transition.getOriginState().getState());
                    System.out.println("destinyState: " + transition.getDestinyState().getState());
                    System.out.println("move: " + transition.getMove());
                    System.out.println("");
                }
            }
            System.out.println("=============================");
        }
    }

    public List<State> runTM(State s) {
        List<State> current = new ArrayList<>();
        List<State> next = new ArrayList<>();
        current.add(s);

        System.out.println("********************************************************");
        System.out.println("*               STARTING TURING MACHINE                *");
        System.out.println("********************************************************");

        for (int i = 0 ; i < numberOfTapes ; i++) {
            canDoTransition.add(false);
        }

        if(debug) {
            System.out.print("current: ");
            for (State stat : current) {
                System.out.println(stat.getState());
            }
        }

        boolean foundGoalState = false;
        while (!foundGoalState) {
            for (State state : current) {
                if (isGoalState(state.getState())) {
                    return current;
                }
            }

            setFalseBooleanArray(canDoTransition);

            for (State c : current) {
                setFalseBooleanArray(canDoTransition);
                setNullTapeTransition(tapes);
                System.out.print("current = ");
                for (Tape tape : tapes) {
                    for (Transition tTransition : tape.getTransitions()) {
                        for (int i = 0 ; i < transitionNumber ; i++) {
                            if (i == tTransition.getTransitionNumber()) {
                                System.out.println("primeiro");
                                if (c.getState().equals(tTransition.getOriginState().getState())) {
                                    System.out.println("segundo");
                                    if (tTransition.getOldSymbol().equals(tape.getTape().get(tape.getCurrentPosition()))) {
                                        System.out.println("terceiro " + i + " " + tape.getTapeNumber());
                                        tape.setActualTransition(tTransition);
                                        canDoTransition.set(tape.getTapeNumber(), true);

                                    }
                                }
                            }
                        }
                    }

                    if (checkBooleanArray(canDoTransition)) {
                        if (!next.contains(tapes.get(0).getActualTransition().getDestinyState())) {
                            System.out.println("added " + tapes.get(0).getActualTransition().getDestinyState().getState());
                            next.add(tapes.get(0).getActualTransition().getDestinyState());
                        }
                        System.out.println("Estado atual: " + tapes.get(0).getActualTransition().getOriginState().getState() + " | Estado destino: " + tapes.get(0).getActualTransition().getDestinyState().getState());
                        for (Tape taps : tapes) {
                            taps.write(taps.getActualTransition().getNewSymbol(), taps.getActualTransition().getMove());
                            System.out.println("***********************************************************************");
                            System.out.println("Fita [" + taps.getTapeNumber() + "] | Simbolo antigo: " + taps.getActualTransition().getOldSymbol() + " | Símbolo novo: " + taps.getActualTransition().getNewSymbol() + " | Movimento: " + taps.getActualTransition().getMove());
                            taps.printTape();
                        }
                        System.out.println("***********************************************************************");
                        System.out.println("Current States: ");
                        for (State sta : current) {
                            System.out.println(sta.getState());
                        }
                        System.out.println("Next States: ");
                        for (State sta : next) {
                            System.out.println(sta.getState());
                        }
                    }
                }
            }
            System.out.println("Next States: ");
            for (State sta : next) {
                System.out.println(sta.getState());
            }

            if (next.isEmpty()) {
                return current;
            }
            current.clear();
            current.addAll(next);
            next.clear();
        }
        return current;
    }

    public Boolean checkBooleanArray(List<Boolean> bool) {
        for (boolean b : bool) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public void setTrueBooleanArray(List<Boolean> bool) {
        for (int i = 0 ; i < bool.size() ; i++) {
            bool.set(i, true);
        }
    }

    public void setFalseBooleanArray(List<Boolean> bool) {
        for (int i = 0 ; i < bool.size() ; i++) {
            bool.set(i, false);
        }
    }

    public boolean isGoalState(String state) {
        for (State s : getGoalStates()) {
            if (s.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInitialState(String state) {
        for (State s : getInitialState()) {
            if (s.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    public List<State> getInitialState() {
        return initialState;
    }

    public void setInitialState(List<State> initialState) {
        this.initialState = initialState;
    }

    public List<State> getGoalStates() {
        return goalStates;
    }

    public void setGoalStates(List<State> goalStates) {
        this.goalStates = goalStates;
    }

    public boolean validateInput(String input) {
        String i[] = input.split("");
        for (String s : i) {
            if (tapeAlphabet.contains(s.trim())) {
                return true;
            }
        }
        return false;
    }

    public List<Tape> getTapes() {
        return tapes;
    }

    public void setTapes(List<Tape> tapes) {
        this.tapes = tapes;
    }

    public State getState(String s) {
        for (State state : states) {
            if (s.equals(state.getState())) {
                return state;
            }
        }
        return null;
    }

    public void setNullTapeTransition(List<Tape> t) {
        for (Tape tape : t) {
            tape.setActualTransition(null);
        }
    }
}