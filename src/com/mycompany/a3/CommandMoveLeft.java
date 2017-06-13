package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move left command
public class CommandMoveLeft extends Command {
	private GameWorld myForm;
	
	public CommandMoveLeft (GameWorld fForm) {
		super("Left");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveShipLeft();
	}
}