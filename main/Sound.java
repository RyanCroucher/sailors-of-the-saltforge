package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
	
	public static void playSound(String soundName, boolean loop) {

		
		try {
			
			File audioFile = new File("./src/sound/" + soundName);
			
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			
			AudioFormat format = audioStream.getFormat();
			
			Info info = new Info(Clip.class, format);
			
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			
			audioClip.open(audioStream);
			audioClip.start();
			
			if (loop)
				audioClip.loop(Clip.LOOP_CONTINUOUSLY);
			
			
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			
		} catch (UnsupportedAudioFileException e) {
			System.err.println(e.getMessage());
			
		} catch (LineUnavailableException e) {
			System.err.println(e.getMessage());
		}
		
	}

}
