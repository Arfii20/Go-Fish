package base.gofish.saveAndMusic;

import javafx.scene.control.Slider;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Music {
    private static Clip audioClip, buttonClick;
    private static double musicVolume, buttonVolume;

    public static void playButtonSoundEffect() {
        if (buttonClick != null) {
            setButtonVolume(buttonVolume);
            buttonClick.setFramePosition(0);
            buttonClick.start();
            return;
        }
        try {
            File file = new File("./src/main/resources/base/click2.wav");
            buttonClick = AudioSystem.getClip();
            buttonClick.open(AudioSystem.getAudioInputStream(file));

            setButtonVolume(buttonVolume);
            buttonClick.setFramePosition(0);
            buttonClick.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("Cannot Play sound");
        }
    }

    public static void setButtonVolume(double percent){
        buttonVolume = percent;
        if (buttonClick != null) {
            FloatControl volumeClick = (FloatControl) buttonClick.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
            try {
                volumeClick.setValue(dB);
            }
            catch (IllegalArgumentException e) {
                // Do nothing
            }
        }
    }

    public static void getMusic(Slider musicVolumeSlider, Slider buttonVolumeSlider) {
        loadVolume(musicVolumeSlider, buttonVolumeSlider);
        if (audioClip == null){
            File file = new File("./src/main/resources/base/track.wav");
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioStream);
                audioClip.loop(audioClip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Cannot Play sound");
                audioClip = null;
            }
        }
        else {
            if (musicVolume > 0) {
                audioClip.start();
            }
        }
        if (audioClip != null) {
            setMusicVolume(musicVolume);
        }
    }

    public static void loadVolume(Slider musicVolumeSlider, Slider buttonVolumeSlider) {
        try (
                FileReader savedLocFile = new FileReader("./settings/musicVolume.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            musicVolume = Double.parseDouble(savedLocStream.readLine());
        }
        catch (IOException e){
            System.out.println("There was an error reading to the music volume file");
            musicVolume = 1.0;
        }
        try (
                FileReader savedLocFile = new FileReader("./settings/buttonVolume.txt");
                BufferedReader savedLocStream = new BufferedReader(savedLocFile)
        ){
            buttonVolume = Double.parseDouble(savedLocStream.readLine());
        }
        catch (IOException e){
            System.out.println("There was an error reading to the button volume file");
            buttonVolume = 1.0;
        }
        musicVolumeSlider.setValue(musicVolume*100);
        buttonVolumeSlider.setValue(buttonVolume*100);
    }

    public static void setMusicVolume(double percent){
        musicVolume = percent;
        if (audioClip != null) {
            FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(percent) / Math.log(10.0) * 20.0);
            try {
                volume.setValue(dB);
            }
            catch (IllegalArgumentException e) {
                // Do nothing
            }
            if (musicVolume > 0) {
                audioClip.start();
            } else {
                audioClip.stop();
            }
        }
    }

    public static void saveVolume() {
        try (
                FileWriter saveMusicFile = new FileWriter("./settings/musicVolume.txt");
                PrintWriter saveMusicWriter = new PrintWriter(saveMusicFile)
        )
        {
            saveMusicWriter.println(musicVolume);
            System.out.println("Music Volume Saved");
        }
        catch (IOException e){
            System.out.println("There was an error writing to the file");
        }
        try (
                FileWriter saveButtonFile = new FileWriter("./settings/buttonVolume.txt");
                PrintWriter saveButtonWriter = new PrintWriter(saveButtonFile)
        )
        {
            saveButtonWriter.println(buttonVolume);
            System.out.println("Button Volume Saved");
        }
        catch (IOException e){
            System.out.println("There was an error writing to the file");
        }
    }
}
