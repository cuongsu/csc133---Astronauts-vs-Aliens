package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

//class needed in order to perform update score/open door command
public class CommandOpenDoor extends Command {
	private GameWorld myForm;
	private Game gameRef;
	
	public CommandOpenDoor (GameWorld fForm, Game game) {
		super("Score");
		myForm = fForm;
		gameRef = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		myForm.openDoor();
		gameRef.playScoopSound();
		if(myForm.getAstroCount() == 0) {
			gameRef.endGame();
		}
	}
}
