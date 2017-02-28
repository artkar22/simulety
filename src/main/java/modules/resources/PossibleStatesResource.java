package modules.resources;

import californium.core.CoapResource;
import californium.core.coap.CoAP;
import californium.core.server.resources.CoapExchange;
import modules.PossibleStatesListWrapper;
import modules.SimuletsState;
import modules.SimuletsStateToSend;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.lang3.SerializationUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static exceptions.ExceptionCodes.CONVERT_ERROR;

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
        final SimuletsStateToSend[] arr = convertListOfPossibleStatesToArray(possibleStates.getAllStates());
        final byte[] dataToSend = SerializationUtils.serialize(arr);
//        YourObject yourObject = (YourObject) SerializationUtils.deserialize(byte[] data)

        exchange.respond(CoAP.ResponseCode.CONTENT, dataToSend);
    }

    private SimuletsStateToSend[] convertListOfPossibleStatesToArray(final List<SimuletsState> allStates) {
        final SimuletsStateToSend[] arr = new SimuletsStateToSend[ allStates.size()];
        for (int x = 0; x < allStates.size(); x++) {
            SimuletsStateToSend toSend = null;
            try {
                toSend = new SimuletsStateToSend(allStates.get(x).getStateId(), imageToByteArray(allStates.get(x).getMiniature()));
            } catch (IOException e) {
                throw new RuntimeException(CONVERT_ERROR);
            }
            arr[x] = toSend;
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

    private static byte[] imageToByteArray(BufferedImage image) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
