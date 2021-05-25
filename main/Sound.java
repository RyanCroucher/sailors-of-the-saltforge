package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Allows soundclips to be played in the game
 * @author Ryan Croucher rcr69
 *
 */
public class Sound {
	
	/**
	 * Plays a sound clip
	 * @param soundName the name of the file to play
	 * @param loop if true, the sound loops forever
	 */
	public static void playSound(String soundName, boolean loop) {

		
		try {	
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(Sound.class.getResource("/sound/" + soundName));
			
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
