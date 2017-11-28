/**
 * Created by Siedg on 24/11/2017.
 */
public class Transition {
    private State destinyState;
    private State originState;
    private String oldSymbol;
    private String newSymbol;
    private String move;
    private int tapeNumber;

    public Transition(State originState, State destinyState, String oldSymbol, String newSymbol, String move, int tapeNumber) {
        this.originState = originState;
        this.destinyState = destinyState;
        this.oldSymbol = oldSymbol;
        this.newSymbol = newSymbol;
        this.move = move;
        this.tapeNumber = tapeNumber;
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
}
