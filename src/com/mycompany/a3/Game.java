package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import java.util.Iterator;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;

//Game class which contains the game variables
//and allows user to input commands in order to 
//control those variables and states of the objects.
public class Game extends Form implements Runnable, ActionListener {
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private UITimer timer = new UITimer(this);
	private int time;
	private BGSound bgMusic;
	private Sound scoreSound;
	private Sound alienSound;
	private Sound attackSound;
	private static boolean pauseCheck = false;
	private boolean muteCheck = false;
	
	private Button buttonExpand = new Button("Expand");
	private Button buttonContract = new Button("Contract");
	private Button buttonOpenDoor = new Button("Open Door");
	private Button buttonRight = new Button("Right");
	private Button buttonLeft = new Button("Left");
	private Button buttonUp = new Button("Up");
	private Button buttonDown = new Button("Down");
	private Button buttonJumpToAstro = new Button("Jump To Astronaut");
	private Button buttonJumpToAlien = new Button("Jump To Alien");
	private Button buttonHeal = new Button ("Heal");
	private Button buttonPause = new Button ("Pause");
	
	private CommandExpand myExpandCommand;
	private CommandContract myContractCommand;
	private CommandOpenDoor myOpenDoorCommand;
	private CommandMoveUp myMoveUpCommand;
	private CommandMoveDown myMoveDownCommand;
	private CommandMoveLeft myMoveLeftCommand;
	private CommandMoveRight myMoveRightCommand;
	private CommandJumpToAstro myJumpToAstroCommand;
	private CommandJumpToAlien myJumpToAlienCommand;
	private CommandAbout myAboutCommand;
	private CommandHelp myHelpCommand;
	private CommandExit myExitCommand;
	private CommandHeal myHealCommand;

	public Game() {
		gw = new GameWorld();
		mv = new MapView(gw);
		sv = new ScoreView();
		gw.addObserver(mv);
		gw.addObserver(sv);
		time = 100;

		this.setTitle("Space Fights Game"); 
		
		//set the color for the content pane's border
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK)); 
		this.setLayout(new BorderLayout());
		
		//create the Command objects needed
		myExpandCommand = new CommandExpand(gw);
		myContractCommand = new CommandContract(gw);
		myOpenDoorCommand = new CommandOpenDoor(gw, this);
		myMoveRightCommand = new CommandMoveRight(gw);
		myMoveLeftCommand = new CommandMoveLeft(gw);		
		myMoveUpCommand = new CommandMoveUp(gw);
		myMoveDownCommand = new CommandMoveDown(gw);
		myJumpToAstroCommand = new CommandJumpToAstro(gw);
		myJumpToAlienCommand = new CommandJumpToAlien(gw);
		myExitCommand = new CommandExit(this);		
		myAboutCommand = new CommandAbout(this);
		myHelpCommand = new CommandHelp(this);
		myHealCommand = new CommandHeal(this);

		//add key bindings to all the commands listed
		addKeyListener('e', myExpandCommand);
		addKeyListener('c', myContractCommand);
		addKeyListener('s', myOpenDoorCommand);
		addKeyListener('u', myMoveUpCommand);
		addKeyListener('d', myMoveDownCommand);
		addKeyListener('l', myMoveLeftCommand);
		addKeyListener('r', myMoveRightCommand);
		addKeyListener('o', myJumpToAstroCommand);
		addKeyListener('a', myJumpToAlienCommand);
		addKeyListener('x', myExitCommand);
		
		//container for left side of the content pane
		Container leftSide = new Container();
		leftSide.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		leftSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		
		//buttons that will be included in the left side container
		buttonExpand = new Button("Expand");
		buttonExpand.setCommand(myExpandCommand);
		buttonExpand.getDisabledStyle().setBgTransparency(255);
		buttonExpand.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonExpand.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonExpand.getUnselectedStyle().setBgTransparency(255);
		buttonExpand.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		buttonExpand.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonExpand.getAllStyles().setMargin(Component.TOP, 130);
		buttonExpand.getAllStyles().setPadding(Component.TOP, 5);
		buttonExpand.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonExpand.getAllStyles().setPadding(Component.LEFT, 5);
		buttonExpand.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonUp = new Button("Up");
		buttonUp.setCommand(myMoveUpCommand);
		buttonUp.getDisabledStyle().setBgTransparency(255);
		buttonUp.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonUp.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonUp.getUnselectedStyle().setBgTransparency(255);
		buttonUp.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonUp.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonUp.getAllStyles().setPadding(Component.TOP, 5);
		buttonUp.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonUp.getAllStyles().setPadding(Component.LEFT, 5);
		buttonUp.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonLeft = new Button("Left");
		buttonLeft.setCommand(myMoveLeftCommand);
		buttonLeft.getDisabledStyle().setBgTransparency(255);
		buttonLeft.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonLeft.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonLeft.getUnselectedStyle().setBgTransparency(255);
		buttonLeft.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonLeft.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonLeft.getAllStyles().setPadding(Component.TOP, 5);
		buttonLeft.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonLeft.getAllStyles().setPadding(Component.LEFT, 5);
		buttonLeft.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonJumpToAstro = new Button("JumpToAstro");
		buttonJumpToAstro.setCommand(myJumpToAstroCommand);
		buttonJumpToAstro.getDisabledStyle().setBgTransparency(255);
		buttonJumpToAstro.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonJumpToAstro.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToAstro.getUnselectedStyle().setBgTransparency(255);
		buttonJumpToAstro.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonJumpToAstro.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToAstro.getAllStyles().setPadding(Component.TOP, 5);
		buttonJumpToAstro.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonJumpToAstro.getAllStyles().setPadding(Component.LEFT, 5);
		buttonJumpToAstro.getAllStyles().setPadding(Component.RIGHT, 5);
		
		//add buttons to the left side container
		leftSide.add(buttonExpand);
		leftSide.add(buttonUp);
		leftSide.add(buttonLeft);
		leftSide.add(buttonJumpToAstro);		
		
		//container for right side of the content pane
		Container rightSide = new Container();
		rightSide.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		rightSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
			
		//buttons that will be on the right side container
		buttonContract = new Button("Contract");
		buttonContract.setCommand(myContractCommand);
		buttonContract.getDisabledStyle().setBgTransparency(255);
		buttonContract.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonContract.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonContract.getUnselectedStyle().setBgTransparency(255);
		buttonContract.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		buttonContract.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonContract.getAllStyles().setMargin(Component.TOP, 130);
		buttonContract.getAllStyles().setPadding(Component.TOP, 5);
		buttonContract.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonContract.getAllStyles().setPadding(Component.LEFT, 5);
		buttonContract.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonDown = new Button("Down");
		buttonDown.setCommand(myMoveDownCommand);
		buttonDown.getUnselectedStyle().setBgTransparency(255);
		buttonDown.getDisabledStyle().setBgTransparency(255);
		buttonDown.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonDown.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonDown.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonDown.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonDown.getAllStyles().setPadding(Component.TOP, 5);
		buttonDown.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonDown.getAllStyles().setPadding(Component.LEFT, 5);
		buttonDown.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonRight = new Button("Right");
		buttonRight.setCommand(myMoveRightCommand);
		buttonRight.getUnselectedStyle().setBgTransparency(255);
		buttonRight.getDisabledStyle().setBgTransparency(255);
		buttonRight.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonRight.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonRight.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 204, 204));
		buttonRight.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonRight.getAllStyles().setPadding(Component.TOP, 5);
		buttonRight.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonRight.getAllStyles().setPadding(Component.LEFT, 5);
		buttonRight.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonOpenDoor = new Button("OpenDoor");
		buttonOpenDoor.setCommand(myOpenDoorCommand);
		buttonOpenDoor.getDisabledStyle().setBgTransparency(255);
		buttonOpenDoor.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonOpenDoor.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonOpenDoor.getUnselectedStyle().setBgTransparency(255);
		buttonOpenDoor.getUnselectedStyle().setBgColor(ColorUtil.rgb(255, 51, 51));
		buttonOpenDoor.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonOpenDoor.getAllStyles().setPadding(Component.TOP, 10);
		buttonOpenDoor.getAllStyles().setPadding(Component.BOTTOM, 10);
		buttonOpenDoor.getAllStyles().setPadding(Component.LEFT, 5);
		buttonOpenDoor.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonJumpToAlien = new Button("JumpToAlien");
		buttonJumpToAlien.setCommand(myJumpToAlienCommand);
		buttonJumpToAlien.getDisabledStyle().setBgTransparency(255);
		buttonJumpToAlien.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonJumpToAlien.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToAlien.getUnselectedStyle().setBgTransparency(255);
		buttonJumpToAlien.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonJumpToAlien.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
		buttonJumpToAlien.getAllStyles().setPadding(Component.TOP, 5);
		buttonJumpToAlien.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonJumpToAlien.getAllStyles().setPadding(Component.LEFT, 5);
		buttonJumpToAlien.getAllStyles().setPadding(Component.RIGHT, 5);

		//add buttons to the right side container
		rightSide.add(buttonContract);
		rightSide.add(buttonDown);
		rightSide.add(buttonRight);
		rightSide.add(buttonJumpToAlien);
		rightSide.add(buttonOpenDoor);
	

		//container for the bottom side of the content pane
		Container bottomSide = new Container();
		bottomSide.setLayout(new FlowLayout(Component.CENTER));
		bottomSide.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		
		buttonHeal.setCommand(myHealCommand);
		buttonHeal.setEnabled(false);
		buttonHeal.getDisabledStyle().setBgTransparency(255);
		buttonHeal.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		buttonHeal.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		buttonHeal.getUnselectedStyle().setBgTransparency(255);
		buttonHeal.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonHeal.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonHeal.getAllStyles().setPadding(Component.TOP, 5);
		buttonHeal.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonHeal.getAllStyles().setPadding(Component.LEFT, 5);
		buttonHeal.getAllStyles().setPadding(Component.RIGHT, 5);
		
		buttonPause.addActionListener(this);
		buttonPause.getUnselectedStyle().setBgTransparency(255);
		buttonPause.getUnselectedStyle().setBgColor(ColorUtil.rgb(0, 128, 255));
		buttonPause.getUnselectedStyle().setFgColor(ColorUtil.YELLOW);
		buttonPause.getAllStyles().setPadding(Component.TOP, 5);
		buttonPause.getAllStyles().setPadding(Component.BOTTOM, 5);
		buttonPause.getAllStyles().setPadding(Component.LEFT, 10);
		buttonPause.getAllStyles().setPadding(Component.RIGHT, 10);
		
		//add buttons to the bottom side container
		bottomSide.add(buttonHeal);
		bottomSide.add(buttonPause);
					
		//adds a toolbar that will include a side menu w/ various options
		//also will include a 'help' option to the right side of the toolbar
		Toolbar gameToolbar = new Toolbar();
		setToolbar(gameToolbar);
		
		//adds the update score/open door command to the side menu
		gameToolbar.addCommandToSideMenu(myOpenDoorCommand);
		
		//set the commands for the sound option on the side menu and
		//on the toolbar
		Command sideMenuSound = new Command("Sound");
		CheckBox checkSideMenuSound = new CheckBox("Sound ON/OFF");
		Command mySideMenuSoundCheck = new SideMenuSoundCheck(this);
		checkSideMenuSound.setSelected(true);
		checkSideMenuSound.setCommand(mySideMenuSoundCheck);
		checkSideMenuSound.getAllStyles().setBgTransparency(255);
		checkSideMenuSound.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		sideMenuSound.putClientProperty("SideComponent", checkSideMenuSound);
		gameToolbar.addCommandToSideMenu(sideMenuSound);
	
		//add about and exit commands to the side menu
		gameToolbar.addCommandToSideMenu(myAboutCommand);
		gameToolbar.addCommandToSideMenu(myExitCommand);
		
		//add 'Help' button to right side of content pane
		gameToolbar.addCommandToRightBar(myHelpCommand);
					
		//add the containers to their respective sides in the content pane, using 
		//border layout
		this.add(BorderLayout.WEST, leftSide);
		this.add(BorderLayout.EAST, rightSide);
		this.add(BorderLayout.SOUTH, bottomSide);
		this.add(BorderLayout.NORTH, sv);
		this.add(BorderLayout.CENTER, mv);
		this.show();
		
		//get and set the dimensions of the game world
		gw.setWidth(mv.getWidth());
		gw.setHeight(mv.getHeight());
		gw.initLayout();
		
		//set the sounds
		bgMusic = new BGSound("Restoring the Light, Facing the Dark.mp3");
		bgMusic.play();	
		scoreSound = new Sound("score.wav");
		alienSound = new Sound("Sound of a Murloc.wav");
		attackSound = new Sound("attack.wav");
		timer.schedule(20,  true,  this);
	}


	//displays general information about the game
	public void displayAbout() {
		Dialog.show("About", "Space Fights Game\nCuong Su\nCSC 133", "Ok", null);	
	}

	//displays all the commands and the keys that will initiate their commands
	public void displayHelp() {
		Dialog.show("Key commands:", "Expand spaceship: 'e'\nContract spaceship: 'c'\nOpen door: 's'\nMove spaceship up, down, left, right: 'u', 'd', 'l', 'r'\n"
				+ "Teleport spaceship to random astronaut location: 'o'\nTeleport spaceship to random alien's location: 'a'\nCreate an alien: 'w'\n"
				+ "Alien/Astronaut fight: 'f'\nTick clock: 't'\nExit the game: 'x'", "Ok", null);		
	} 
	
	//sets the sound check box on or off depending whether check is true or false
	public void setCheckBox (boolean check) {
		if (check) {
			sv.setSoundLabel("ON");
			muteCheck = false;
			if(!muteCheck && !pauseCheck) {
				bgMusic.play();
			}
		} else {
			sv.setSoundLabel("OFF");
			bgMusic.pause();
			muteCheck = true;
		}
	}
	
	//allows healing of an astronaut; only usable in pause mode
	public void healAstro() {
		for(int i = 0; i < gw.getObjectList().size(); i++) {
			if(gw.getObjectList().get(i) instanceof Astronaut) {
				Astronaut tempAstro = (Astronaut)gw.getObjectList().get(i);
				if(tempAstro.isSelected()) {
					tempAstro.heal();
				}
			}
		}
	}
	
	public void actionPerformed (ActionEvent evt) {
		pauseCheck = !pauseCheck;
		//check if the game is paused
		if(pauseCheck) { 
			buttonPause.setText("Play");
			//stops the timer and all objects from moving
			timer.cancel(); 
			buttonHeal.setEnabled(true); 

			buttonExpand.setEnabled(false);
			buttonUp.setEnabled(false);
			buttonDown.setEnabled(false);
			buttonContract.setEnabled(false);
			buttonLeft.setEnabled(false);
			buttonRight.setEnabled(false);
			buttonOpenDoor.setEnabled(false);
			buttonJumpToAstro.setEnabled(false);
			buttonJumpToAlien.setEnabled(false);
			bgMusic.pause();
			
			//Unbind all keys
			removeKeyListener('s', myOpenDoorCommand);
			removeKeyListener('u', myMoveUpCommand);
			removeKeyListener('d', myMoveDownCommand);
			removeKeyListener('l', myMoveLeftCommand);
			removeKeyListener('r', myMoveRightCommand);
			removeKeyListener('e', myExpandCommand);
			removeKeyListener('c', myContractCommand);
			removeKeyListener('o', myJumpToAstroCommand);
			removeKeyListener('a', myJumpToAlienCommand);
			
		} else { //if in 'Play' mode
			buttonPause.setText("Pause  ");
			//re-enable the timer and allow objects to move again
			timer.schedule(20, true, this); 
			buttonHeal.setEnabled(false);
			
			buttonExpand.setEnabled(true);
			buttonUp.setEnabled(true);
			buttonDown.setEnabled(true);
			buttonContract.setEnabled(true);
			buttonLeft.setEnabled(true);
			buttonRight.setEnabled(true);
			buttonOpenDoor.setEnabled(true);
			buttonJumpToAstro.setEnabled(true);
			buttonJumpToAlien.setEnabled(true);
			myOpenDoorCommand.setEnabled(true);
			
			//only re-enable background music if sound is not muted
			if(muteCheck == false) {
				bgMusic.play();
			}
			
			//Re-enable keys
			addKeyListener('s', myOpenDoorCommand);
			addKeyListener('u', myMoveUpCommand);
			addKeyListener('d', myMoveDownCommand);
			addKeyListener('l', myMoveLeftCommand);
			addKeyListener('r', myMoveRightCommand);
			addKeyListener('e', myExpandCommand);
			addKeyListener('c', myContractCommand);
			addKeyListener('o', myJumpToAstroCommand);
			addKeyListener('a', myJumpToAlienCommand);
			
			//De-select all astronauts
			for(int i = 0; i < gw.getObjectList().size(); i++) {
				if(gw.getObjectList().get(i) instanceof Astronaut) {
					Astronaut tempAstro = (Astronaut)gw.getObjectList().get(i);
					tempAstro.setSelected(false);
				}
			}
		}
	}
	
	//asks the user whether they want to exit the game, and exits upon answering 'ok'
	public void displayExit() {
		Boolean exitCheck = Dialog.show("Exiting the game", "Are you sure you want to exit the game?", "Ok", "Cancel");
		if (exitCheck) {
			Display.getInstance().exitApplication();
		}
	}
	
	//dialog that shows after players rescues all astronauts
	public void endGame() {
		Boolean endCheck = Dialog.show("Final Score: " + gw.getScore(), "You saved all the astronauts!", "Exit game", null);
		if (endCheck) {
			Display.getInstance().exitApplication();
		}
	}
	
	public static boolean isPaused() {
		return pauseCheck;
	}
	
	//run method that's called by timer schedule; deals with collision
	public void run() {
		//causes all opponents to move and updates the map view
		gw.tick(time);
		GameObjectsCollection listRef = gw.getObjectList();
		//search through the entire collection of game objects, not including the spaceship
		for(int i = 1; i < listRef.size(); i++) { 
			//check if the first object selected is an astronaut
			if(listRef.get(i) instanceof Astronaut) { 
				//temp variable for the astronaut selected
				Astronaut currentAstro = (Astronaut)listRef.get(i);
				//check the collision counter; if the counter is above 100, clear the collision set to allow collisions for this astronaut
				if(currentAstro.getCounter() > 100) { 
					currentAstro.clearColSet();
				}
				currentAstro.incrementCounter(); 
				for(int j = 1; j < listRef.size(); j++) {
					//check if the second object is an alien
					if(listRef.get(j) instanceof Alien) { 
						//check the astronaut's collision set to see if it does not contain this specific alien
						if(!currentAstro.getColSet().contains(listRef.get(j))) { 
							//check to see if the two objects are colliding 
							if(currentAstro.collidesWith((ICollider)listRef.get(j))) { 
								//astronaut-alien fight
								currentAstro.handleCollision((ICollider)listRef.get(j), gw.getObjectList(), gw, this); 
								//add the alien to the astronaut's collision set , so the same collision doesn't happen again within 100 counts
								currentAstro.addToColSet(listRef.get(j)); 
								//reset the counter
								currentAstro.clearCounter();
							}
						}
					}
				}
			}
			//check if the first object selected is an alien
			if(listRef.get(i) instanceof Alien) { 
				//temp variable for the alien selected
				Alien currentAlien = (Alien)listRef.get(i); 
				currentAlien.incrementCounter();	
				//check the collision counter
				if(currentAlien.getCounter() > 100) {
					//increment birth to see if the current alien is allowed to spawn a new alien
					currentAlien.incBirth();
					currentAlien.clearColSet();
					for(int k = 1; k < listRef.size(); k++) {
						//check if the second object is an alien
						if(listRef.get(k) instanceof Alien) { 
							//check to see that the first alien is not the same as the second alien
							if(currentAlien != listRef.get(k)) {
								//check to see if the second alien is not in the first alien's collision set
								if(!currentAlien.getColSet().contains(listRef.get(k))) { 
									//check to see if the two objects are colliding
									if(currentAlien.collidesWith((ICollider)listRef.get(k))) { 
										//add the second alien to the first alien's collision set
										currentAlien.addToColSet(listRef.get(k));
										//create new alien
										currentAlien.handleCollision((ICollider)listRef.get(k), gw.getObjectList(), gw, this);	
										//reset the counter
										currentAlien.clearCounter();									
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void playScoopSound() {
		if(!muteCheck) {
			scoreSound.play();
		}
	}
	
	public void playAttackSound() {
		if(!muteCheck) {
			attackSound.play();
		}
	}

	public void playAlienSound() {
		if(!muteCheck) {
			alienSound.play();
		}
	}
}
