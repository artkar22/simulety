package modules.resources;

import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;

public class IdResource extends CoapResource {

    private IpsoDigitalInputImpl digitalInput;
    private final int simuletsId;
    private final static String ID = "id";

    public IdResource(final int simuletsId) {
        super(ID);
        this.simuletsId = simuletsId;
    }

    public IdResource(final int simuletsId, final IpsoDigitalInputImpl digitalInput) {
        super(ID);
        this.simuletsId = simuletsId;
        this.digitalInput = digitalInput;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        if (digitalInput != null) {
            digitalInput.setMobileAppAddress(exchange.getSourceAddress());
            digitalInput.setMobileAppPort(exchange.getSourcePort());
        }
        exchange.respond(ResponseCode.CONTENT, Integer.toString(simuletsId));
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
