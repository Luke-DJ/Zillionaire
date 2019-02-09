import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

//Created by Luke Dicken - Jones
public class MainMenuGUI
{
    //Declaring the variables for the swing components
    private JPanel mainMenuPanel;
    private JLabel titleLabel;
    private JButton playButton;
    private JButton settingsButton;
    private JButton quitButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    MainMenuGUI(GameController gameController)
    {
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();

        uiController.changeTitle("Zillionaire - Main Menu");

        //If there are no sounds currently playing, the 'menu_music' sound is played (looping)
        if (soundController.silent())
        {
            soundController.PlaySound("menu_music", true, false);
        }

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(mainMenuPanel);
        uiController.refreshFonts(mainMenuPanel);

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'RulesGUI' panel
        playButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, true);
            uiController.changeFramePanel(new RulesGUI(gameController).getRulesPanel());
        });

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'SettingsGUI' panel
        settingsButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new SettingsGUI(gameController).getSettingsPanel());
        });

        //Plays the 'select' sound, and exits the program
        quitButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, true);
            gameController.exitGame();
        });
    }


    //Getter for this GUI's panel
    JPanel getMainMenuPanel()
    {
        return mainMenuPanel;
    }
}