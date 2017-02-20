package modules.resources;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * Created by Inni on 2017-01-11.
 */
public class NameResource extends CoapResource {
    private String simuletsName;
    private static final String NAME = "NAME";

    public NameResource(String name) {
        super(NAME);
        simuletsName = name;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.CONTENT, simuletsName);
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
