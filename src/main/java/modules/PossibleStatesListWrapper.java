package modules;

import java.util.List;

import static exceptions.ExceptionCodes.NO_SUCH_A_STATE;


/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class PossibleStatesListWrapper {
    private final List<SimuletsState> possibleStates;

    public PossibleStatesListWrapper(final List<SimuletsState> possibleStates) {
        this.possibleStates = possibleStates;
    }

    public SimuletsState getStateById(final String id) {
        return possibleStates.stream().filter(simuletsState -> id.equals(simuletsState.getStateId())).findAny().orElse(null);
    }

    public List<SimuletsState> getAllStates() {
        return possibleStates;
    }
}
