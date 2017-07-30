package modules.resources;

import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP;
import californium.core.server.resources.CoapExchange;

/**
 * Created by Inni on 2017-01-11.
 */
public class ClassResource extends CoapResource {

    private String className;
    private static final String CLASS = "class";

    public ClassResource(String className) {
        super(CLASS);
        this.className = className;
    }

    @Override
    public void handleGET(CoapExchange exchange) {

        exchange.respond(CoAP.ResponseCode.CONTENT, className);
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
