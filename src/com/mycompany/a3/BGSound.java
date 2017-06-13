package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable{
	
	private Media m;
	
	public BGSound(String fileName){
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(),
					"/"+fileName);
			m = MediaManager.createMedia(is, "audio/mp3", this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void pause(){ m.pause();} //pause the sound
	public void play(){ m.play();} //continue playing from where it left off
	
	//entered when media has finished playing
	public void run() {
		//start playing from beginning of the sound file
		m.setTime(0);
		m.play();
	}
}
