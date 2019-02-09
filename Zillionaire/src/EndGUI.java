import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;

//Created by Luke Dicken - Jones
public class EndGUI
{
    //Declaring the variables for the swing components
    private JPanel endPanel;
    private JLabel titleLabel;
    private JLabel winnersLabel;
    private JScrollPane leaderboardScrollPane;
    private JTextArea leaderboardTextArea;
    private JButton mainMenuButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    EndGUI(GameController gameController)
    {
        PlayersController playersController = gameController.getPlayersController();
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();

        uiController.changeTitle("Zillionaire - Game over");

        soundController.PlaySound("end_music", true, false);

        //Removing 'leaderboardScrollPane's default border
        leaderboardScrollPane.setBorder(BorderFactory.createEmptyBorder());

        //Populates the text of 'winnersLabel' and 'leaderboardTextArea' using 'playersController'
        uiController.populateLeaderboard(
                playersController,
                endPanel,
                winnersLabel,
                leaderboardScrollPane,
                leaderboardTextArea);

        //Ensuring 'winnersLabel's text fits within the component
        uiController.shrinkFontToComponent(winnersLabel, winnersLabel.getText());

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(endPanel);
        uiController.refreshFonts(endPanel);

        /*Ends all the currently playing sounds,
        changes the currently displayed panel to a new 'MainMenuGUI' panel, and plays the 'select' sound*/
        mainMenuButton.addActionListener(e ->
        {
            soundController.endAll();
            uiController.changeFramePanel(new MainMenuGUI(gameController).getMainMenuPanel());
            soundController.PlaySound("select", false, false);
        });
    }


    //Getter for this GUI's panel
    JPanel getEndPanel()
    {
        return endPanel;
    }
}