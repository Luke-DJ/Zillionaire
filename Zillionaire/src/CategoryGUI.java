import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

//Created by Luke Dicken - Jones
public class CategoryGUI
{
    //Declaring the variables for the swing components
    private JPanel categoryPanel;
    private JLabel titleLabel;
    private JButton generalKnowledgeButton;
    private JButton sportButton;
    private JButton countriesButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    CategoryGUI(GameController gameController)
    {
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();
        int currentQuestionNumber = gameController.getCurrentQuestionNumber();

        uiController.changeTitle("Zillionaire - Category");

        //Adding 'currentQuestionNumber to the text of 'titleLabel'
        titleLabel.setText(titleLabel.getText() + currentQuestionNumber);

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(categoryPanel);
        uiController.refreshFonts(categoryPanel);

        //Plays the 'select' sound, and changes the currently displayed panel to a new "general" 'AnswersGUI' panel
        generalKnowledgeButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new QuestionGUI(
                    gameController,
                    new AnswersGUI(gameController, "general").getAnswersPanel()).getQuestionPanel());
        });

        //Plays the 'select' sound, and changes the currently displayed panel to a new "sport" 'AnswersGUI' panel
        sportButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new QuestionGUI(gameController,
                    new AnswersGUI(gameController, "sport").getAnswersPanel()).getQuestionPanel());
        });

        //Plays the 'select' sound, and changes the currently displayed panel to a new "countries" 'AnswersGUI' panel
        countriesButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new QuestionGUI(gameController,
                    new AnswersGUI(gameController, "countries").getAnswersPanel()).getQuestionPanel());
        });
    }


    //Getter for this GUI's panel
    JPanel getCategoryPanel()
    {
        return categoryPanel;
    }
}