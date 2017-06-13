package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.CheckBox;
import com.codename1.ui.events.ActionEvent;

//allows changing of the status of side menu sound option
//can be on or off
public class SideMenuSoundCheck extends Command {
	private Game myForm;
	
	public SideMenuSoundCheck (Game fForm) {
		super("Sound ON/OFF");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed (ActionEvent evt) {
		if (((CheckBox)evt.getComponent()).isSelected())
			myForm.setCheckBox(true);
		else
			myForm.setCheckBox(false);
		SideMenuBar.closeCurrentMenu();
	}
}
