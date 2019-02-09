import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

//Created by Luke Dicken - Jones
public class SoundController
{
    //Declaring the variable for the 'ArrayList' of sounds
    private ArrayList<Clip> sounds;
    //Declaring and instantiating the variable for the path to the 'sounds' folder
    private String soundsPath = "resources/sounds/";


    //Constructor - instantiating the class's instance variables
    public SoundController()
    {
        sounds = new ArrayList<>();
    }


    //<editor-fold defaultstate="collapsed" desc="Attempts to play a sound with a given name, which loops/ends the currently playing sounds based on given booleans">
        /*The string 'path' is created using 'soundsPath'
        The array of 'File's 'allSounds' is created, and is populated with all the files in 'path'
        'allSounds' is iterated over, and if a file is found with the same name as the given sound name,
        then 'path' becomes the path to the matching file
        If the given 'endAll' boolean is true, 'endAll' is called to end all currently playing sounds
        The 'Clip' 'clip' is created, and is added to 'sounds'
        'clip' is 'opened' with the audio data present in the file at 'path', and is then started
        If the given 'loop' boolean is true, 'clip' is set to repeat itself indefinitely
        If any part of this is unsuccessful, the method is terminated and consequently no sound is played*/
    //</editor-fold>
    public void PlaySound(String name, boolean loop, boolean endAll)
    {
        String path = soundsPath;
        try
        {
            File[] allSounds = new File(path).listFiles();
            for (int i = 0; i < allSounds.length; i++)
            {
                if (allSounds[i].getName().toLowerCase().contains(name.toLowerCase() + "."))
                {
                    path = allSounds[i].getPath().replace("\\", "/");
                    break;
                }
                else if (i == allSounds.length - 1)
                {
                    return;
                }
            }
        }
        catch (Exception e)
        {
            return;
        }

        if (endAll)
        {
            endAll();
        }

        try
        {
            Clip clip = AudioSystem.getClip();
            sounds.add(clip);
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
            if (loop)
            {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        catch (Exception ignored)
        {
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Ends all currently playing sounds, and removes them from 'sounds'">
    //'sounds' is iterated over, and each sound is 'stopped', followed by 'sounds' being cleared
    //</editor-fold>
    void endAll()
    {
        try
        {
            for (Clip sound : sounds)
            {
                sound.stop();
            }
            sounds.clear();
        }
        catch (Exception ignored)
        {
        }
    }

    //Returns true if there are no sounds playing ('sounds' is empty)
    boolean silent()
    {
        return sounds.isEmpty();
    }
}