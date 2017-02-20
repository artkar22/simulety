package modules.Lampka.resources;

import Protocol.Comm_Protocol;
import main.Menu;
import modules.Lampka.IpsoLightControl;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class StatusResource extends CoapResource {

    private IpsoLightControl ipsoLightControl;
    private Menu menu;
    private static final String STATUS = "on_off";

    public StatusResource(IpsoLightControl ipsoLightControl, Menu menu) {
        super(STATUS);
        this.ipsoLightControl = ipsoLightControl;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        if (ipsoLightControl.getOn_Off_Status() == IpsoLightControl.LAMP_SWITCHED_ON) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControl.LAMP_SWITCHED_ON));
        } else if (ipsoLightControl.getOn_Off_Status() == IpsoLightControl.LAMP_SWITCHED_OFF) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(IpsoLightControl.LAMP_SWITCHED_OFF));
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status put");
        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
            ipsoLightControl.switchOn();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
            ipsoLightControl.switchOff();
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);

        }
        else
        {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
}
