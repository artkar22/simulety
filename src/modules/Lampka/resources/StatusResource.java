package modules.Lampka.resources;

import Protocol.Comm_Protocol;
import main.Menu;
import modules.Lampka.IpsoLightControlImpl;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class StatusResource extends CoapResource {

    private IpsoLightControlImpl ipsoLightControlImpl;
    private Menu menu;
    private static final String STATUS = "on_off";

    public StatusResource(IpsoLightControlImpl ipsoLightControlImpl, Menu menu) {
        super(STATUS);
        this.ipsoLightControlImpl = ipsoLightControlImpl;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        if (ipsoLightControlImpl.getOn_Off_Status() == IpsoLightControlImpl.LAMP_SWITCHED_ON) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControlImpl.LAMP_SWITCHED_ON));
        } else if (ipsoLightControlImpl.getOn_Off_Status() == IpsoLightControlImpl.LAMP_SWITCHED_OFF) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControlImpl.LAMP_SWITCHED_OFF));
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status put");
        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
            ipsoLightControlImpl.switchOn();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
            ipsoLightControlImpl.switchOff();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        }
        else
        {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
}
