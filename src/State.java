import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siedg on 24/11/2017.
 */
public class State {
    private String state;
    private List<Transition> transitions;
    private boolean isInitial;
    private boolean isGoal;

    public State(String state, boolean isInitial, boolean isGoal) {
        this.state = state;
        this.isInitial = isInitial;
        this.isGoal = isGoal;
        this.transitions = new ArrayList<>();
    }

    public boolean finalState(State state, String oldSymbol, String newSymbol, String move) {
        for (Transition t : transitions) {
            if (t.getDestinyState().getState().equals(state.getState())) {
                if (oldSymbol.equals(t.getOldSymbol())) {
                    if (t.getMove().equals(move)) {
                        if (newSymbol.equals(t.getNewSymbol())) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public void print() {
        System.out.println("Estado: " + this.state + " Estado Inicial: " + (isInitial ? "Sim" : "Não") + " Estado final: " + (isGoal ? "Sim" : "Não") + "Transições: ");
        for (Transition t : this.transitions) {
            t.print();
            System.out.println("");
        }
    }

    public void addTransition(Transition t) {
        transitions.add(t);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setFinal(boolean goal) {
        this.isGoal = isGoal;
    }
}
