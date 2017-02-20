package serverResources;

import main.Menu;
import modules.Trigger.BistableTrigger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TriggerResource extends CoapResource {

    private BistableTrigger bistableTrigger;
    private Menu menu;

    public TriggerResource(BistableTrigger bistableTrigger, Menu menu) {
        super(bistableTrigger.getNameOfSimulet());
        this.bistableTrigger = bistableTrigger;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(ResponseCode.CONTENT, bistableTrigger.getCurrentState().getStateId());
//        if (bistableTrigger.getState() == BistableTrigger.TRIGGER_SWITCHED_ON) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_ON));
//        } else if (bistableTrigger.getState() == BistableTrigger.TRIGGER_SWITCHED_OFF) {
//            exchange.respond(ResponseCode.CONTENT, Boolean.toString(BistableTrigger.TRIGGER_SWITCHED_OFF));
//        }
    }

//    @Override
//    public void handlePUT(CoapExchange exchange) {
//        if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)) {
//            budzik.switchOn();
//            menu.repaint();
//            exchange.respond(ResponseCode.CHANGED);
//
//        } else if (exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF)) {
//            budzik.switchOff();
//            menu.repaint();
//            exchange.respond(ResponseCode.CHANGED);
//
//        }
//    }
}
