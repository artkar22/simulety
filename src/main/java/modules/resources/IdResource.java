package modules.resources;

import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;

public class IdResource extends CoapResource {

    private final String simuletsId;
    private final static String ID = "id";

    public IdResource(final String simuletsId) {
        super(ID);
        this.simuletsId = simuletsId;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(ResponseCode.CONTENT, simuletsId);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }
}
