package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform move to astronaut command
public class CommandJumpToAstro extends Command {
	private GameWorld myForm;
	
	public CommandJumpToAstro (GameWorld fForm) {
		super("MoveToAstro");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.jumpToAstro();
	}
}