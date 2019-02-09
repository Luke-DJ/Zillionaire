import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JColorChooser;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

//Created by Luke Dicken - Jones
public class SettingsGUI
{
    //Declaring the variables for the swing components
    private JPanel settingsPanel;
    private JLabel titleLabel;
    private JScrollPane playersScrollPane;
    private JList playersList;
    private JPanel playerManagerPanel;
    private JButton addButton;
    private JTextField playerInputTextField;
    private JButton removeButton;
    private JLabel playersLabel;
    private JPanel colourSettingsPanel;
    private JButton setForegroundColourButton;
    private JButton setBackgroundColourButton;
    private JButton menuButton;
    private JButton addAQuestionButton;
    private JPanel playersListPanel;
    private JPanel reorderButtonsPanel;
    private JButton upButton;
    private JButton downButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    SettingsGUI(GameController gameController)
    {
        PlayersController playersController = gameController.getPlayersController();
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();

        uiController.changeTitle("Zillionaire - Settings");

        playersList.setModel(playersController.getPlayers());

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(settingsPanel);
        uiController.refreshFonts(settingsPanel);

        /*Setting the font name of the text of 'upButton' and 'downButton' to
        "DialogInput" (as the arrow symbols work better with this font name)*/
        for (JButton arrowButton : new ArrayList<>(Arrays.asList(upButton, downButton)))
        {
            Font arrowButtonFont = arrowButton.getFont();
            arrowButton.setFont(new Font("DialogInput", arrowButtonFont.getStyle(), arrowButtonFont.getSize()));
        }

        /*Setting the text of 'playerInputTextField' to the currently selected 'Player'
        in 'playersList' when the selection is changed (if the selection isn't empty)*/
        playersList.addListSelectionListener(e ->
        {
            if (!playersList.isSelectionEmpty())
            {
                playerInputTextField.setText(playersList.getSelectedValue().toString());
            }
        });

        //Moves the currently selected 'Player' in the players list model up one position
        upButton.addActionListener(e -> playersController.movePlayerUp(playersList));

        //Moves the currently selected 'Player' in the players list model down one position
        downButton.addActionListener(e -> playersController.movePlayerDown(playersList));

        /*Attempts to add a player using the text in 'playerInputTextField' to the players list model,
        and clears 'playerInputTextField's text if the new player is added successfully*/
        addButton.addActionListener(e ->
        {
            if (playersController.addPlayer(playerInputTextField.getText()))
            {
                playerInputTextField.setText("");
            }
        });

        /*Attempts to remove a player using the text in 'playerInputTextField' from the players list model,
        and clears 'playerInputTextField's text if the player is removed successfully*/
        removeButton.addActionListener(e ->
        {
            if (playersController.removePlayer(playerInputTextField.getText()))
            {
                playerInputTextField.setText("");
            }
        });

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'AddQuestionGUI' panel
        addAQuestionButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new AddQuestionGUI(gameController).getAddQuestionPanel());
        });

        /*Plays the 'select' sound,
        changes 'gameFrame's foreground colour to a new colour (chosen using a JColorChooser),
        and changes the foreground and background colours of the components in the panel to those of 'gameFrame'*/
        setForegroundColourButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeForegroundColour(soundController, JColorChooser.showDialog(
                    null,
                    "Set foreground colour",
                    uiController.getGameFrame().getForeground()));
            uiController.refreshColours(settingsPanel);
        });

        /*Plays the 'select' sound,
        changes 'gameFrame's background colour to a new colour (chosen using a JColorChooser),
        and changes the foreground and background colours of the components in the panel to those of 'gameFrame'*/
        setBackgroundColourButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeBackgroundColour(soundController, JColorChooser.showDialog(
                    null,
                    "Set background colour",
                    uiController.getGameFrame().getBackground()));
            uiController.refreshColours(settingsPanel);
        });

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'MainMenuGUI' panel
        menuButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new MainMenuGUI(gameController).getMainMenuPanel());
        });
    }


    //Getter for this GUI's panel
    JPanel getSettingsPanel()
    {
        return settingsPanel;
    }
}