package modules;

import modules.Trigger.BistableTrigger;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<SimuletsState> getAllStates(String className) {
        if(className != null && className.equals("CounterTrigger")){
            return (possibleStates.stream().filter(simuletsState -> !simuletsState.getStateId().equals(BistableTrigger.TRIGGER_SWITCHED_OFF))).collect(Collectors.toList());
        }
        return possibleStates;
    }
}
