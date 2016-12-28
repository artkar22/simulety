package modules.resources;

import Protocol.Comm_Protocol;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import main.Menu;
import modules.Trigger.Trigger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class DigitalInputStateResource extends CoapResource {

    private IpsoDigitalInputImpl digitalInput;
    private Menu menu;
    private static final String STATUS = "on_off";

    public DigitalInputStateResource(IpsoDigitalInputImpl digitalInput, Menu menu) {
        super(STATUS);
        this.digitalInput = digitalInput;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(Trigger.TRIGGER_SWITCHED_ON));
        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(Trigger.TRIGGER_SWITCHED_OFF));
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status put");
        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
            digitalInput.switchOn();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
            digitalInput.switchOff();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);
        } else {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
}
