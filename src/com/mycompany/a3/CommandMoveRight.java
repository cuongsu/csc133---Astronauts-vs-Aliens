package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move right command
public class CommandMoveRight extends Command {
	private GameWorld myForm;
	
	public CommandMoveRight (GameWorld fForm) {
		super("Right");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveShipRight();
	}
}