package modules.resources;

import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;
import modules.SimuletsState;
import modules.Trigger.BistableTrigger;

import static app.PossibleStatesConsts.START_TIMER;

public class CounterSimuletCurrentStateResource extends CoapResource {

    private static final String STATUS = "current_state";
    private static final String NO_ACTION_FLAG = "no_action";
    private BistableTrigger digitalInput;
    private Menu menu;
    private SimuletsState initialState;

    private static int time = 10000;
    public CounterSimuletCurrentStateResource(BistableTrigger digitalInput, Menu menu, SimuletsState initialState) {
        super(STATUS);
        this.digitalInput = digitalInput;
        this.menu = menu;
        this.initialState = initialState;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        exchange.respond(ResponseCode.CONTENT, digitalInput.getCurrentState().getStateId());

//        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_ON));
//        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_OFF));
//        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(exchange.getRequestText()));
        exchange.respond(ResponseCode.CHANGED);
        if(START_TIMER.equals(exchange.getRequestText())){
            sendTriggerChange();
        }
//            menu.repaint();
    }
    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.METHOD_NOT_ALLOWED);
    }

    public static void setTime(int timeToSet) {
        time = timeToSet *1000;
    }

    private void sendTriggerChange() {
        menu.repaint();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        menu.repaint();
                        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_ON));
                        changed();
                        digitalInput.setCurrentState(initialState);
                    }
                },
                time
        );
    }
}
