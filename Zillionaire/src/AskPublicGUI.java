import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

//Created by Luke Dicken - Jones
public class AskPublicGUI
{
    //Declaring the variables for the swing components
    private JPanel askPublicPanel;
    private JLabel titleLabel;
    private JPanel barsPanel;
    private JProgressBar bar1;
    private JProgressBar bar2;
    private JProgressBar bar3;
    private JProgressBar bar4;
    private JTextArea potentialAnswerTextArea1;
    private JTextArea potentialAnswerTextArea2;
    private JTextArea potentialAnswerTextArea3;
    private JTextArea potentialAnswerTextArea4;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    AskPublicGUI(GameController gameController, String correctAnswer, ArrayList<JButton> buttons)
    {
        PlayersController playersController = gameController.getPlayersController();
        UIController uiController = gameController.getUiController();
        String imagesPath = uiController.getImagesPath();
        RandomController randomController = gameController.getRandomController();

        //Setting up the "Ask the Public" JFrame
        JFrame frame = new JFrame("Ask the Public");
        frame.setContentPane(this.askPublicPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(imagesPath + "icon.png").getImage());
        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(imagesPath + "cursor.png").getImage()
                        .getScaledInstance(32, 32, Image.SCALE_DEFAULT),
                new Point(0, 0),
                "Cursor"));
        frame.pack();
        frame.setVisible(true);

        /*Populates the text of the potential answer TextAreas with the text of the potential answer Buttons, and
        populates the values of the ProgressBars with random values (based on certain factors) using randomController*/
        uiController.populatePublicBars(
                randomController,
                gameController.getCurrentQuestionNumber(),
                new ArrayList<>(Arrays.asList(
                        potentialAnswerTextArea1,
                        potentialAnswerTextArea2,
                        potentialAnswerTextArea3,
                        potentialAnswerTextArea4)),
                buttons,
                correctAnswer,
                new ArrayList<>(Arrays.asList(bar1, bar2, bar3, bar4)),
                playersController.getPlayerHelpFacilityUsed(
                        gameController.getCurrentPlayerIndex(),
                        "halfandhalf"),
                playersController.getPlayerHelpFacilityUsed(
                        gameController.getCurrentPlayerIndex(),
                        "clue"));

        /*Formatting the TextAreas (replacing them with a Panel containing Labels,
        resembling centre-aligned versions of the TextAreas)*/
        for (JTextArea textArea : Arrays.asList(
                potentialAnswerTextArea1,
                potentialAnswerTextArea2,
                potentialAnswerTextArea3,
                potentialAnswerTextArea4))
        {
            uiController.formatTextArea(textArea, true);
        }

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(askPublicPanel);
        uiController.refreshFonts(askPublicPanel);
    }
}