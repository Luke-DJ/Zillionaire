import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

//Created by Luke Dicken - Jones
public class PlayerIntroGUI
{
    //Declaring the variables for the swing components
    private JPanel playerIntroPanel;
    private JLabel playerIntroLabel;
    private JButton beginButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    PlayerIntroGUI(GameController gameController)
    {
        PlayersController playersController = gameController.getPlayersController();
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();
        int currentPlayerIndex = gameController.getCurrentPlayerIndex();

        uiController.changeTitle("Zillionaire - " + playersController.getPlayerName(currentPlayerIndex));

        /*Setting the text of 'playerIntroLabel' to "It is '", plus the name of the
        'Player' at currentPlayerIndex (using 'playersController'), plus "'s turn"*/
        playerIntroLabel.setText("It is '" + playersController.getPlayerName(currentPlayerIndex) + "'s turn");

        //Ensuring 'playerIntroLabel's text fits within the component
        uiController.shrinkFontToComponent(playerIntroLabel, playerIntroLabel.getText());

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(playerIntroPanel);
        uiController.refreshFonts(playerIntroPanel);

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'CategoryGUI' panel
        beginButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new QuestionGUI(
                    gameController,
                    new CategoryGUI(gameController).getCategoryPanel()).getQuestionPanel());
        });
    }


    //Getter for this GUI's panel
    JPanel getPlayerIntroPanel()
    {
        return playerIntroPanel;
    }
}