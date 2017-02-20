package modules;

import java.util.List;

import static exceptions.ExceptionCodes.NO_SUCH_A_STATE;

/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class PossibleStatesListWrapper {
    final private List<SimuletsState> possibleStates;

    public PossibleStatesListWrapper(final List<SimuletsState> possibleStates) {
        this.possibleStates = possibleStates;
    }

    public SimuletsState getStateById(final String id) {
        return possibleStates.stream().findFirst().filter(simuletsState -> id.equals(simuletsState.getStateId())).orElseThrow(() -> new RuntimeException(NO_SUCH_A_STATE));
    }
}
