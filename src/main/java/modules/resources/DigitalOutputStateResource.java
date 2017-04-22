package modules.resources;

import Protocol.Comm_Protocol;
import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;
import modules.SimuletsState;
//SIMULETY - NIE TRIGGERY
public class DigitalOutputStateResource extends CoapResource {

    private IpsoDigitalOutputImpl digitalOutput;
    private Menu menu;
    private static final String STATUS = "current_status";

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
    public void handlePOST(CoapExchange exchange) {
        System.out.println("Status POST");
        final SimuletsState newState = digitalOutput.getPossibleStates().getStateById(exchange.getRequestText());
        if(newState!=null){
            digitalOutput.setCurrentState(digitalOutput.getPossibleStates().getStateById(exchange.getRequestText()));
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);
        }else {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }
}
