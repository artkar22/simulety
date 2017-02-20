package modules.Lampka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Menu;

public class LampkaActionListener implements ActionListener {

	private IpsoLightControlImpl ipsoLightControlImpl;
	private Menu menu;
	public LampkaActionListener(IpsoLightControlImpl ipsoLightControlImpl, Menu menu) {
		this.ipsoLightControlImpl = ipsoLightControlImpl;
		this.menu = menu;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		ipsoLightControlImpl.switchOn();
		menu.repaint();
	}

}
