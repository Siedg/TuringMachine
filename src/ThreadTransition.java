import java.util.List;

/**
 * Created by Siedg on 11/12/2017.
 */
public class ThreadTransition {
    private List<State> current;
    private List<State> next;
    private List<Tape> currentTapes;
    private int id;

    public ThreadTransition(List<State> current, List<State> next, List<Tape> currentTapes, int id) {
        this.current = current;
        this.next = next;
        this.currentTapes = currentTapes;
        this.id = id;
    }

    public List<State> getCurrent() {
        return current;
    }

    public void setCurrent(List<State> current) {
        this.current = current;
    }

    public List<State> getNext() {
        return next;
    }

    public void setNext(List<State> next) {
        this.next = next;
    }

    public List<Tape> getCurrentTapes() {
        return currentTapes;
    }

    public void setCurrentTapes(List<Tape> currentTapes) {
        this.currentTapes = currentTapes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
