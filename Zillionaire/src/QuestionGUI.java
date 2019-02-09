import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

//Created by Luke Dicken - Jones
public class QuestionGUI
{
    //Declaring the variables for the swing components
    private JPanel questionPanel;
    private JPanel progressBarPanel;
    private JProgressBar questionsProgressBar;
    private JLabel nameLabel;
    private JLabel moneyLabel;
    private JPanel statsPanel;
    private JPanel contentsPanel;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    QuestionGUI(GameController gameController, JPanel newContentsPanel)
    {
        UIController uiController = gameController.getUiController();
        int currentQuestionNumber = gameController.getCurrentQuestionNumber();

        uiController.changeTitle("Zillionaire - Q" + currentQuestionNumber);

        //Populates 'nameLabel', 'moneyLabel', and 'questionsProgressBar', with values managed by 'gameController'
        uiController.populateQuestionPanel(gameController, nameLabel, moneyLabel, questionsProgressBar);

        //Ensuring 'nameLabel' and 'moneyLabel's text fits within their components
        uiController.shrinkFontToComponent(nameLabel, nameLabel.getText());
        uiController.shrinkFontToComponent(moneyLabel, moneyLabel.getText());

        //Replaces 'contentsPanel' with 'newContentsPanel'
        uiController.replacePanel(contentsPanel, newContentsPanel);

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(questionPanel);
        uiController.refreshFonts(questionPanel);
    }


    //Getter for this GUI's panel
    JPanel getQuestionPanel()
    {
        return questionPanel;
    }
}