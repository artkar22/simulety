package modules.resources;

import californium.core.CoapResource;
import californium.core.coap.CoAP;
import californium.core.server.resources.CoapExchange;
import modules.PossibleStatesListWrapper;
import modules.SimuletsState;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class PossibleStatesResource extends CoapResource {
    private PossibleStatesListWrapper possibleStates;
    private static final String NAME = "POSSIBLE_STATES";
    public PossibleStatesResource(final PossibleStatesListWrapper possibleStates) {
        super(NAME);
        this.possibleStates = possibleStates;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        final SimuletsState[] arr = convertListOfPossibleStatesToArray(possibleStates.getAllStates());
        final byte[] dataToSend = SerializationUtils.serialize(arr);
//        YourObject yourObject = (YourObject) SerializationUtils.deserialize(byte[] data)

        exchange.respond(CoAP.ResponseCode.CONTENT, dataToSend);
    }

    private SimuletsState[] convertListOfPossibleStatesToArray(final List<SimuletsState> allStates) {
        final SimuletsState[] arr = new SimuletsState[allStates.size()];
        for (int x = 0; x < allStates.size(); x++) {
            arr[x] = allStates.get(x);
        }
        return arr;
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }
}
