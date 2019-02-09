import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

//Created by Luke Dicken - Jones
public class RulesGUI
{
    //Declaring the variables for the swing components
    private JPanel rulesPanel;
    private JLabel titleLabel;
    private JButton startButton;
    private JTextArea rulesTextArea;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    RulesGUI(GameController gameController)
    {
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();

        uiController.changeTitle("Zillionaire - Rules");

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(rulesPanel);
        uiController.refreshFonts(rulesPanel);

        /*Formatting 'rulesTextArea' (replacing it with a Panel containing
        Labels, resembling a centre-aligned version of 'rulesTextArea')*/
        uiController.formatTextArea(rulesTextArea, false);

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'PlayerIntroGUI' panel
        startButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new PlayerIntroGUI(gameController).getPlayerIntroPanel());
        });
    }


    //Getter for this GUI's panel
    JPanel getRulesPanel()
    {
        return rulesPanel;
    }
}