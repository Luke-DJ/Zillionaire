import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JSlider;

//Created by Luke Dicken - Jones
public class AddQuestionGUI
{
    //Declaring the variables for the swing components
    private JPanel addQuestionPanel;
    private JLabel titleLabel;
    private JPanel questionAttributes1Panel;
    private JLabel categoryLabel;
    private JComboBox categoryComboBox;
    private JLabel questionNumberLabel;
    private JSlider questionNumberSlider;
    private JPanel questionAttributes2Panel;
    private JLabel questionLabel;
    private JScrollPane questionScrollPane;
    private JTextArea questionTextArea;
    private JLabel correctAnswerLabel;
    private JScrollPane correctAnswerScrollPane;
    private JTextArea correctAnswerTextArea;
    private JPanel questionAttributes3Panel;
    private JLabel incorrectAnswer1Label;
    private JScrollPane incorrectAnswer1ScrollPane;
    private JTextArea incorrectAnswer1TextArea;
    private JLabel incorrectAnswer2Label;
    private JScrollPane incorrectAnswer2ScrollPane;
    private JTextArea incorrectAnswer2TextArea;
    private JLabel incorrectAnswer3Label;
    private JScrollPane incorrectAnswer3ScrollPane;
    private JTextArea incorrectAnswer3TextArea;
    private JPanel questionAttributes4Panel;
    private JLabel hintLabel;
    private JScrollPane hintScrollPane;
    private JTextArea hintTextArea;
    private JButton addButton;
    private JButton returnToSettingsButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    AddQuestionGUI(GameController gameController)
    {
        UIController uiController = gameController.getUiController();
        QuestionsController questionsController = gameController.getQuestionsController();
        SoundController soundController = gameController.getSoundController();

        uiController.changeTitle("Zillionaire - Add a question");

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(addQuestionPanel);
        uiController.refreshFonts(addQuestionPanel);

        //Attempts to add the entered question info to the file that corresponds with the category and question number
        addButton.addActionListener(e -> questionsController.addQuestion(
                (String) categoryComboBox.getSelectedItem(),
                questionNumberSlider.getValue(),
                questionTextArea.getText(),
                correctAnswerTextArea.getText(),
                incorrectAnswer1TextArea.getText(),
                incorrectAnswer2TextArea.getText(),
                incorrectAnswer3TextArea.getText(),
                hintTextArea.getText()));

        //Plays the 'select' sound, and changes the currently displayed panel to a new 'SettingsGUI' panel
        returnToSettingsButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            uiController.changeFramePanel(new SettingsGUI(gameController).getSettingsPanel());
        });
    }


    //Getter for this GUI's panel
    JPanel getAddQuestionPanel()
    {
        return addQuestionPanel;
    }
}