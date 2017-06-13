package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move down command
public class CommandMoveDown extends Command {
	private GameWorld myForm;
	
	public CommandMoveDown (GameWorld fForm) {
		super("Down");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.moveShipDown();
	}
}
