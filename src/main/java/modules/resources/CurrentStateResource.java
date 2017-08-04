package modules.resources;

import californium.core.coap.CoAP;
import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;
import modules.Simulet;
import modules.SimuletsState;
//SIMULETY - NIE TRIGGERY
public class CurrentStateResource extends CoapResource {

    private Simulet digitalOutput;
    private Menu menu;
    private Integer timeToResetToInitial;
    private static final String STATUS = "current_state";

    public CurrentStateResource(Simulet digitalOutput, String timeToResetToInitial, Menu menu) {
        super(STATUS);
        this.digitalOutput = digitalOutput;
        this.menu = menu;
        if(timeToResetToInitial != null)
            this.timeToResetToInitial = Integer.valueOf(timeToResetToInitial);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        exchange.respond(ResponseCode.CONTENT, digitalOutput.getCurrentState().getStateId());
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("Status POST");
        final SimuletsState newState = digitalOutput.getPossibleStates().getStateById(exchange.getRequestText());
        if(newState!=null){
            digitalOutput.setCurrentState(digitalOutput.getPossibleStates().getStateById(exchange.getRequestText()));
            menu.repaint();
            exchange.respond(ResponseCode.CHANGED);
            if(timeToResetToInitial != null) {
                changeStateToInitialAfterTime();
            }
        }else {
            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
        }
    }

    private void changeStateToInitialAfterTime() {

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if(menu.getInitialState() != null){
                            digitalOutput.setCurrentState(menu.getInitialState());
                        }
                        menu.repaint();
                    }
                },
                timeToResetToInitial.intValue() * 1000
        );
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }
    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }

}
