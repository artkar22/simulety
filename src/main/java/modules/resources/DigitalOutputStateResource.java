package modules.resources;

import Protocol.Comm_Protocol;
import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;

public class DigitalOutputStateResource extends CoapResource {

    private IpsoDigitalOutputImpl digitalOutput;
    private Menu menu;
    private static final String STATUS = "on_off";

    public DigitalOutputStateResource(IpsoDigitalOutputImpl digitalOutput, Menu menu) {
        super(STATUS);
        this.digitalOutput = digitalOutput;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        exchange.respond(ResponseCode.CONTENT, digitalOutput.getCurrentState().getStateId());
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status put");
        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
            digitalOutput.switchOn();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
            digitalOutput.switchOff();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);
        }
        else
        {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
}
