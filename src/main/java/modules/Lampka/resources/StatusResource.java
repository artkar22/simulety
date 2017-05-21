package modules.Lampka.resources;

import Protocol.Comm_Protocol;
import app.Menu;
import modules.Lampka.IpsoLightControl;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;
import modules.SimuletsState;

public class StatusResource extends CoapResource {

    private IpsoLightControl ipsoLightControl;
    private Menu menu;
    private static final String STATUS = "current_status";

    public StatusResource(IpsoLightControl ipsoLightControl, Menu menu) {
        super(STATUS);
        this.ipsoLightControl = ipsoLightControl;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        exchange.respond(ResponseCode.CONTENT, ipsoLightControl.getCurrentState().getStateId());
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status post");
        final SimuletsState newState = ipsoLightControl.getPossibleStates().getStateById(exchange.getRequestText());
        if(newState!=null){
            ipsoLightControl.setCurrentState(ipsoLightControl.getPossibleStates().getStateById(exchange.getRequestText()));
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);
        }else {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }
}
