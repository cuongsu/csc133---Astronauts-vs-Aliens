package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move to alien command
public class CommandJumpToAlien extends Command {
	private GameWorld myForm;
	
	public CommandJumpToAlien (GameWorld fForm) {
		super("MoveToAlien");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.jumpToAlien();
	}
}