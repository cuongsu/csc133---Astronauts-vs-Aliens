package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class necessary needed in order to perform the action of expanding rescuers
public class CommandExpand extends Command {
	private GameWorld myForm;
	
	public CommandExpand (GameWorld fForm) {
		super("Expand");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.expand();
	}
}
