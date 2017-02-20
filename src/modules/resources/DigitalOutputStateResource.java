package modules.resources;

import Protocol.Comm_Protocol;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import main.Menu;
import modules.Lampka.IpsoLightControlImpl;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

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
        if (digitalOutput.getState() == digitalOutput.SWITCHED_ON) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControlImpl.LAMP_SWITCHED_ON));
        } else if (digitalOutput.getState() == digitalOutput.SWITCHED_OFF) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControlImpl.LAMP_SWITCHED_OFF));
        }
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
