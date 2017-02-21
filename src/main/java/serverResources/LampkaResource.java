package serverResources;

import app.Menu;
import modules.Lampka.IpsoLightControl;

import californium.core.CoapResource;
import californium.core.coap.CoAP.ResponseCode;
import californium.core.server.resources.CoapExchange;

import Protocol.Comm_Protocol;

public class LampkaResource extends CoapResource {

	private IpsoLightControl ipsoLightControl;
	private Menu menu;
	public LampkaResource(IpsoLightControl ipsoLightControl, Menu menu) {
		super("IPSO");//TODO
		this.ipsoLightControl = ipsoLightControl;
		this.menu = menu;
	}

	@Override
	public void handleGET(CoapExchange exchange) 
	{
//		if(ipsoLightControl.getCurrentStatus()==IpsoLightControl.BUDZIK_SWITCHED_ON)
//		{
//			exchange.respond(ResponseCode.CONTENT, Integer.toString(IpsoLightControl.BUDZIK_SWITCHED_ON));
//		}
//		else if(ipsoLightControl.getCurrentStatus()==IpsoLightControl.BUDZIK_SWITCHED_OFF)
//		{
//			exchange.respond(ResponseCode.CONTENT, Integer.toString(IpsoLightControl.BUDZIK_SWITCHED_OFF));
//		}
	}
	@Override
	public void handlePUT(CoapExchange exchange) 
	{
		if(exchange.getRequestText().equals(Comm_Protocol.SWITCHED_ON)){
		ipsoLightControl.switchOn();
		menu.repaint();
		exchange.respond(ResponseCode.CHANGED);
		
		}
		else if(exchange.getRequestText().equals(Comm_Protocol.SWITCHED_OFF))
		{
			ipsoLightControl.switchOff();
			menu.repaint();
			exchange.respond(ResponseCode.CHANGED);

		}
	}
}
