package modules.Lampka;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Menu;

public class LampkaActionListener implements ActionListener {

	private IpsoLightControl ipsoLightControl;
	private Menu menu;
	public LampkaActionListener(IpsoLightControl ipsoLightControl, Menu menu) {
		this.ipsoLightControl = ipsoLightControl;
		this.menu = menu;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		ipsoLightControl.switchOn();
		menu.repaint();
	}

}
