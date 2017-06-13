package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class necessary to perform the action of exiting the game
public class CommandExit extends Command {
	private Game myForm;
	
	public CommandExit (Game fForm) {
		super("Exit");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.displayExit();
	}
}