package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class that allows healing of astronauts
public class CommandHeal extends Command {
	private Game myForm;
	
	public CommandHeal (Game fForm) {
		super("Heal");
		myForm = fForm;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.healAstro();
	}
}