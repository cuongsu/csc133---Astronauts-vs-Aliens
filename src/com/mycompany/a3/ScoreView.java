package com.mycompany.a3;

import java.util.Observer;
import java.util.Observable;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

//One of the two Views
//Located in the north container primary BorderLayout
//Updates score and astronaut/alien data using Labels
public class ScoreView extends Container implements Observer {
	
	private GameWorld scoreUpdate;
	private Label labelScore = new Label("Score:  0       ");	
	private Label labelAstroRescued = new Label("Astronauts Rescued:  0");
	private Label labelAliensCaught = new Label("Aliens Caught:  0      ");
	private Label labelAstrosLeft = new Label("Astronauts left:  4");
	private Label labelAliensLeft = new Label("Aliens left:  3      ");
	private Label labelSound = new Label("Sound:   ON");
	
	//Initialize Label colors/padding
	public ScoreView() {
		setLayout(new FlowLayout(Component.CENTER));
		getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.BLACK));
		
		labelScore.getStyle().setFgColor(ColorUtil.BLACK);
		labelScore.getStyle().setPadding(Component.LEFT, 1);
		labelScore.getStyle().setPadding(Component.RIGHT, 17);
		
		labelAstroRescued.getStyle().setFgColor(ColorUtil.BLACK);
		labelAstroRescued.getStyle().setPadding(Component.LEFT, 1);
		labelAstroRescued.getStyle().setPadding(Component.RIGHT, 2);

		labelAliensCaught.getStyle().setFgColor(ColorUtil.BLACK);
		labelAliensCaught.getStyle().setPadding(Component.LEFT, 2);
		labelAliensCaught.getStyle().setPadding(Component.RIGHT, 7);
		
		labelAstrosLeft.getStyle().setFgColor(ColorUtil.BLACK);
		labelAstrosLeft.getStyle().setPadding(Component.LEFT, 2);
		labelAstrosLeft.getStyle().setPadding(Component.RIGHT, 2);

		labelAliensLeft.getStyle().setFgColor(ColorUtil.BLACK);
		labelAliensLeft.getStyle().setPadding(Component.LEFT, 2);
		labelAliensLeft.getStyle().setPadding(Component.RIGHT, 7);
		
		labelSound.getStyle().setFgColor(ColorUtil.BLACK);
		labelSound.getStyle().setPadding(Component.LEFT, 2);
		labelSound.getStyle().setPadding(Component.RIGHT, 2);
		
		//add the labels to the container
		add(labelScore);
		add(labelAstroRescued);
		add(labelAliensCaught);
		add(labelAstrosLeft);
		add(labelAliensLeft);
		add(labelSound);
	}
	
	public void setSoundLabel(String text) {
		labelSound.setText("Sound:   " + text);
	}

	//obtain the updated information and send it to game world
	public void update (Observable o, Object arg) {
		scoreUpdate = (GameWorld) o;
		
		labelScore.setText("Score:  " + scoreUpdate.getScore());
		labelScore.getStyle().setPadding(Component.LEFT, 1);
		labelScore.getStyle().setPadding(Component.RIGHT, 17);
		labelAstroRescued.setText("Astronauts Rescued:  " + scoreUpdate.getAstroRescued());
		labelAliensCaught.setText("Aliens Caught:  " + scoreUpdate.getAliensCaught());
		labelAliensCaught.getStyle().setPadding(Component.LEFT, 2);
		labelAliensCaught.getStyle().setPadding(Component.RIGHT, 7);
		labelAstrosLeft.setText("Astronauts left:  " + scoreUpdate.getAstroCount());
		labelAliensLeft.setText("Aliens left:  " + scoreUpdate.getAlienCount());
		labelAliensLeft.getStyle().setPadding(Component.LEFT, 2);
		labelAliensLeft.getStyle().setPadding(Component.RIGHT, 7);		
	}
	
}
