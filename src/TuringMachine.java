import javafx.collections.transformation.TransformationList;

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
    private Tape tape;
    private boolean deadlock;
    private int numberOfTapes;
    private int cycle = 0;
    private List<Tape> tapes;
    private List<TransitionList> listTapeTransition;
    private List<String> inputTapeContent;
    public boolean debug;


    public TuringMachine(List<String> contents, List<String> inputTapeContent, boolean debug) {
        this.debug = debug;
        this.inputTapeContent = inputTapeContent;
        loadMachine(contents);


        for (String input : inputTapeContent) {
            if (validateInput(input)) {
                List<State> endStates = getEndStates(getInitialState().get(0));

                System.out.println("========================================================");
                System.out.println("Finalizado:");

                boolean foundGoalState = false;
                for (State s : endStates) {
                    if (s.isGoal()) {
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
                }

                System.out.println("========================================================");
                System.out.println("Fitas: ");
                for (Tape t : getTapes()) {
                    System.out.println("Fita [" + t.getTapeNumber() + "]:");
                    t.printTape();
                }
            } else {
                System.out.println("Palavra inválida.");
            }
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
            initialState.add(new State("", true, false));
        }

        if (debug) {
            System.out.println("ESTADO INICIAL OK!");
        }

        // Line 6 Final States
        goalStates = new ArrayList<>();
        String line6[] = contents.get(5).split(" ");
        for (String s : line6) {
            initialState.add(new State("", false, true));
        }

        if (debug) {
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
        //numberOfTapes = Integer.parseInt(split[6].trim());
        tapes = new ArrayList<>();

        // Line 8-EOF Transitions
        transitions = new ArrayList<>();
        listTapeTransition = new ArrayList<>();

        for (int i = 7 ; i < contents.size() ; i++) {
            String line[] = contents.get(i).split(" ");

            if (debug) {
                for (String s : line) {
                    //System.out.println(s);
                }
            }

            State originState = new State(line[0], isInitialState(line[0]), isGoalState(line[0]));
            State destinyState = new State(line[1], isInitialState(line[0]), isGoalState(line[0]));

            int j = 2;
            int currentTape = 0;
            while (j < line[j].length()) {
                System.out.println("Fita: " + currentTape);
                String oldSymbol = line[j];
                j++;
                String newSymbol = line[j];
                j++;
                String move = line[j];
                j++;

                if (debug) {
                    System.out.println("===");
                    System.out.println(inputTapeContent);
                    System.out.println("tapes.size(): " + tapes.size());
                }

                tapes.add(new Tape(inputTapeContent.get(currentTape), blankSymbol, currentTape));

                if (debug) {
                    System.out.println("tapes.size(): " + tapes.size());
                    System.out.println("originState: " + originState.getState());
                    System.out.println("destinyState: " + destinyState.getState());
                    System.out.println("oldSymbol: " + oldSymbol);
                    System.out.println("newSymbol: " + newSymbol);
                    System.out.println("move: " + move);
                    System.out.println("currentTape: " + currentTape);
                }

                tapes.get(currentTape).addTransition(new Transition(originState, destinyState, oldSymbol, newSymbol, move, currentTape));
                currentTape++;

                if (debug) {
                    System.out.println("===");
                    System.out.println(tapes.size());
                }
                //listTapeTransition.add(new TransitionList(j));
                //listTapeTransition.get(j).addTransition(new Transition(originState, destinyState, oldSymbol, newSymbol, move));
            }
        }

        for (State state : states) {
            for (Tape tape : tapes) {
                for (Transition trans : tape.getTransitions()) {
                    if (state.getState().equals(trans.getOriginState().getState())) {
                        for (Transition t : state.getTransitions()) {
                            if (t == trans) {
                                state.addTransition(trans);
                            }
                        }
                    }
                }
            }
        }

        /*
        for (State initial : initialState) {
            for (Tape tape : tapes) {
                for (Transition trans : tape.getTransitions()) {
                    if (initial.getState().equals(trans.getOriginState().getState())) {
                        for (Transition t : initial.getTransitions()) {
                            if (t == trans) {
                                initial.addTransition(trans);
                            }
                        }
                    }
                }
            }
        }

        for (State goal : goalStates) {
            for (Tape tape : tapes) {
                for (Transition trans : tape.getTransitions()) {
                    if (goal.getState().equals(trans.getOriginState().getState())) {
                        for (Transition t : goal.getTransitions()) {
                            if (t == trans) {
                                goal.addTransition(trans);
                            }
                        }
                    }
                }
            }
        }
        */
        /*
        // Load all destiny states
        for (State s : states) {
            for (Transition t : transitions) {
                if (s.getState().equals(t.getOriginState().getState())) {
                    for (Transition trans : s.getTransitions()) {
                        // If transition is not in state
                        if (t == trans) {
                            s.addTransition(t);
                        }
                        //if (!trans.getDestinyState().getState().equals(s.getState()) && t.getOldSymbol().equals(trans.getOldSymbol()) && t.getMove().equals(trans.getMove()) && t.getNewSymbol().equals(trans.getNewSymbol())) {
                        //    s.addTransition(t);
                        //}
                    }
                }
            }
        }
        */
    }

    public List<State> getEndStates(State s) {
        List<State> current = new ArrayList<>();
        List<State> next = new ArrayList<>();
        current.add(s);
        cycle = -1;

        boolean foundGoalState = false;
        while (!foundGoalState) {
            for (State state : current) {
                if (state.isGoal()) {
                    return current;
                }
            }

            for (Tape t : tapes) {
                String tapeSymbol = t.getTape().get(t.getCurrentPosition());
                for (State c : current) {
                    for (Transition transition : c.getTransitions()) {
                        if (transition.getOldSymbol().equals(tapeSymbol) && !next.contains(transition.getDestinyState())) {
                            next.add(transition.getDestinyState());
                            t.write(transition.getNewSymbol(), transition.getMove());
                            System.out.println("Símbolo antigo: " + transition.getOldSymbol() + " Símbolo novo: " + transition.getNewSymbol());
                        }
                    }
                }
            }

            if (next.isEmpty()) {
                return current;
            }

            /*
            String symbol = tape.getTape().get(tape.getCurrentPosition());
            for (State c : current) {
                for (Transition t : c.getTransitions()) {
                    if (t.getOldSymbol().equals(symbol) && (!next.contains(t.getDestinyState()))) {
                        next.add(t.getDestinyState());
                        tape.write(t.getOldSymbol(), t.getMove());
                        System.out.println("Símbolo antigo: " + t.getOldSymbol() + " Símbolo novo: " + symbol);
                    }
                }
            }

            if (next.isEmpty()) {
                return current;
            }
            current = next;
            */
        }
        return current;
    }

    public boolean isGoalState(String state) {
        for (State s : goalStates) {
            if (s.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInitialState(String state) {
        for (State s : initialState) {
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
}