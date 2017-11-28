import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siedg on 25/11/2017.
 */
public class TransitionList {
    private int tapeNumber;
    private List<Transition> listTransitions;

    public TransitionList (int tapeNumber) {
        this.tapeNumber = tapeNumber;
        listTransitions = new ArrayList<>();
    }

    public void addTransition(Transition t) {
        listTransitions.add(t);
    }
}
