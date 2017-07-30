package modules.resources;

import californium.core.CoapResource;
import californium.core.coap.CoAP;
import californium.core.server.resources.CoapExchange;
import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;

/**
 * Created by Inni on 2017-01-11.
 */
public class ClassResource extends CoapResource {

    private IpsoDigitalInputImpl digitalInput;
    private String className;
    private static final String CLASS = "class";

    public ClassResource(String className) {
        super(CLASS);
        this.className = className;
    }

    public ClassResource(String className, final IpsoDigitalInputImpl digitalInput) {
        super(CLASS);
        this.className = className;
        this.digitalInput = digitalInput;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        if (digitalInput != null) {
            digitalInput.setMobileAppAddress(exchange.getSourceAddress());
            digitalInput.setMobileAppPort(exchange.getSourcePort());
        }

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
