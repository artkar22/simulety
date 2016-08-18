package modules.resources;

import modules.Lampka.Lampka;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class IdResource extends CoapResource {

    private Lampka lampka;
    private final static String ID = "id";

    public IdResource(Lampka lampka) {
        super(ID);
        this.lampka = lampka;

    }

    @Override
    public void handleGET(CoapExchange exchange) {

        exchange.respond(ResponseCode.CONTENT, Integer.toString(lampka.getId()));
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }
}
