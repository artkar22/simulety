package serverResources;

import main.Menu;
import modules.Lampka.IpsoLightControlImpl;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import Protocol.Comm_Protocol;

public class LampkaResource extends CoapResource {

	private IpsoLightControlImpl ipsoLightControlImpl;
	private Menu menu;
	public LampkaResource(IpsoLightControlImpl ipsoLightControlImpl, Menu menu) {
		super("IPSO");//TODO
		this.ipsoLightControlImpl = ipsoLightControlImpl;
		this.menu = menu;
	}

	@Override
	public void handleGET(CoapExchange exchange) 
	{
//		if(ipsoLightControlImpl.getCurrentStatus()==IpsoLightControlImpl.BUDZIK_SWITCHED_ON)
//		{
//			exchange.respond(ResponseCode.CONTENT, Integer.toString(IpsoLightControlImpl.BUDZIK_SWITCHED_ON));
//		}
//		else if(ipsoLightControlImpl.getCurrentStatus()==IpsoLightControlImpl.BUDZIK_SWITCHED_OFF)
//		{
//			exchange.respond(ResponseCode.CONTENT, Integer.toString(IpsoLightControlImpl.BUDZIK_SWITCHED_OFF));
//		}
	}
	@Override
	public void handlePUT(CoapExchange exchange) 
	{
		if(exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)){
		ipsoLightControlImpl.switchOn();
		menu.repaint();
		exchange.respond(ResponseCode.CHANGED);
		
		}
		else if(exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF))
		{
			ipsoLightControlImpl.switchOff();
			menu.repaint();
			exchange.respond(ResponseCode.CHANGED);

		}
	}
}
