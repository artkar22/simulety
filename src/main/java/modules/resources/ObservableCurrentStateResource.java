package modules.resources;

import californium.core.coap.CoAP;
import app.Menu;
import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;
import modules.Trigger.BistableTrigger;

public class ObservableCurrentStateResource extends CoapResource {

    private static final String STATUS = "current_state";
    private static final String NO_ACTION_FLAG = "no_action";
    private BistableTrigger digitalInput;
    private Menu menu;
    private boolean buttonActionFlag = false;

    public ObservableCurrentStateResource(BistableTrigger digitalInput, Menu menu) {
        super(STATUS);
        this.digitalInput = digitalInput;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("Status get");
        if (buttonActionFlag == false) {
            exchange.respond(ResponseCode.CONTENT, NO_ACTION_FLAG);
        }
        exchange.respond(ResponseCode.CONTENT, digitalInput.getCurrentState().getStateId());

//        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_ON));
//        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_OFF));
//        }
    }

    public BistableTrigger getDigitalInput() {
        return digitalInput;
    }

    public void setButtonActionFlagTrue() {
        this.buttonActionFlag = true;
    }

    public void setButtonActionFlagFalse() {
        this.buttonActionFlag = false;
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(exchange.getRequestText()));
//        System.out.println("Status put");
//        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
//            digitalInput.switchOn();
            menu.repaint();
//            exchange.respond(ResponseCode.CHANGED);
//
//        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
//            digitalInput.switchOff();
//            menu.repaint();
//            exchange.respond(ResponseCode.CHANGED);
//        }
//
//
//         else {
//            exchange.respond(ResponseCode.NOT_ACCEPTABLE);
//        }
    }
    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

}
