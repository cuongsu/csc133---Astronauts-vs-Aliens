package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move up command
public class CommandMoveUp extends Command {
	private GameWorld myForm;
	
	public CommandMoveUp (GameWorld fForm) {
		super("Up");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveShipUp();
	}
}