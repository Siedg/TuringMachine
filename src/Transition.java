/**
 * Created by Siedg on 24/11/2017.
 */
public class Transition implements Cloneable {
    private State destinyState;
    private State originState;
    private String oldSymbol;
    private String newSymbol;
    private String move;
    private int transitionNumber;
    private int tapeNumber;

    public Transition(State originState, State destinyState, String oldSymbol, String newSymbol, String move, int tapeNumber, int transitionNumber) {
        this.originState = originState;
        this.destinyState = destinyState;
        this.oldSymbol = oldSymbol;
        this.newSymbol = newSymbol;
        this.move = move;
        this.tapeNumber = tapeNumber;
        this.transitionNumber = transitionNumber;
    }

    @Override
    public Transition clone() {
        try {
            return (Transition) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void print() {
        System.out.println("Origem: " + this.originState.getState() + " " + "Destino: " + this.destinyState.getState() + " " + "Símbolo atual: " + this.getOldSymbol() + " " + "Símbolo novo: " + this.getNewSymbol() + " " + "Movimento: " + this.getMove());
    }

    public State getDestinyState() {
        return destinyState;
    }

    public void setDestinyState(State destinyState) {
        this.destinyState = destinyState;
    }

    public State getOriginState() {
        return originState;
    }

    public void setOriginState(State originState) {
        this.originState = originState;
    }

    public String getOldSymbol() {
        return oldSymbol;
    }

    public void setOldSymbol(String oldSymbol) {
        this.oldSymbol = oldSymbol;
    }

    public String getNewSymbol() {
        return newSymbol;
    }

    public void setNewSymbol(String newSymbol) {
        this.newSymbol = newSymbol;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public int getTapeNumber() {
        return tapeNumber;
    }

    public void setTapeNumber(int tapeNumber) {
        this.tapeNumber = tapeNumber;
    }

    public int getTransitionNumber() {
        return transitionNumber;
    }

    public void setTransitionNumber(int transitionNumber) {
        this.transitionNumber = transitionNumber;
    }

    public void printTransition() {;
        System.out.println("tape: " + this.getTapeNumber());
        System.out.println("oldSymbol: " + this.getOldSymbol());
        System.out.println("newSymbol: " + this.getNewSymbol());
        System.out.println("originState: " + this.getOriginState().getState());
        System.out.println("destinyState: " + this.getDestinyState().getState());
        System.out.println("move: " + this.getMove());
        System.out.println();
    }
}
