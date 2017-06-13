package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class necessary in order to perform the action of contraction of rescuers
public class CommandContract extends Command {
	private GameWorld myForm;
	
	public CommandContract (GameWorld fForm) {
		super("Contract");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.contract();
	}
}