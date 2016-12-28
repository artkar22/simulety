package modules.resources;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import modules.Lampka.Lampka;
import modules.Radio.Radio;
import modules.Wiatraczek.Wiatraczek;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class IdResource extends CoapResource {

    private Lampka lampka;
    private IpsoDigitalOutputImpl digitalOutput;
    private IpsoDigitalInputImpl digitalInput;
    private int simuletsId;
    private final static String ID = "id";

    public IdResource(Lampka lampka) {
        super(ID);
        this.lampka = lampka;
        this.simuletsId = lampka.getId();
    }
    public IdResource(IpsoDigitalOutputImpl digitalOutput) {
        super(ID);
        this.digitalOutput = digitalOutput;
        this.simuletsId = digitalOutput.getId();
    }
    public IdResource(IpsoDigitalInputImpl digitalInput) {
        super(ID);
        this.digitalInput = digitalInput;
        this.simuletsId = digitalInput.getId();
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        if(digitalInput != null){
            digitalInput.setMobileAppAddress(exchange.getSourceAddress());
            digitalInput.setMobileAppPort(exchange.getSourcePort());
        }
        exchange.respond(ResponseCode.CONTENT, Integer.toString(simuletsId));
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(ResponseCode.FORBIDDEN);
    }
}
