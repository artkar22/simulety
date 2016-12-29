package modules.Trigger;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import main.Menu;
import modules.resources.DigitalInputStateResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class TriggerActionListener implements ActionListener {
    private Trigger trigger;
    private Menu menu;
    private DigitalInputStateResource on_off_resource;

    public TriggerActionListener(final Trigger trigger, final DigitalInputStateResource on_off_resource, final Menu menu) {
        this.trigger = trigger;
        this.on_off_resource = on_off_resource;
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        sendTriggerChange();
//        trigger.switchOn();
//        menu.repaint();
    }

    private void sendTriggerChange() {
        on_off_resource.setButtonActionFlagTrue();
        final IpsoDigitalInputImpl digitalInput = on_off_resource.getDigitalInput();
        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
            digitalInput.setState(digitalInput.SWITCHED_OFF);
        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
            digitalInput.setState(digitalInput.SWITCHED_ON);
        }
        on_off_resource.changed();
        on_off_resource.setButtonActionFlagFalse();
//        server.add(on_off_Resource);
//        server.get
//        if (trigger.getMobileAppAddress() != null) {
//            final Endpoint myEndpoint = server.getEndpoint(trigger.getSimuletsAddress());
//            myEndpoint.
//            myEndpoint.sendRequest(request());
//            myEndpoint.
//        }
    }

//    private Request request() {
//        final Response resp = Response.createResponse();
//        try {
//            request.setURI(new URI(trigger.getMobileAppAddress().toString() +":"+ Integer.toString(trigger.getMobileAppPort())));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        request.setPayload(TRIGGERED);
//        return request;
//    }
}

