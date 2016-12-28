package serverResources;

import Protocol.Comm_Protocol;
import main.Menu;
import modules.Budzik.Budzik;
import modules.Trigger.Trigger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TriggerResource extends CoapResource {

    private Trigger trigger;
    private Menu menu;

    public TriggerResource(Trigger trigger, Menu menu) {
        super(trigger.getNameOfSimulet());
        this.trigger = trigger;
        this.menu = menu;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        if (trigger.getState() == Trigger.TRIGGER_SWITCHED_ON) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(Trigger.TRIGGER_SWITCHED_ON));
        } else if (trigger.getState() == Trigger.TRIGGER_SWITCHED_OFF) {
            exchange.respond(ResponseCode.CONTENT, Boolean.toString(Trigger.TRIGGER_SWITCHED_OFF));
        }
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
