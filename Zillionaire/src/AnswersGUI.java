import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Arrays;

//Created by Luke Dicken - Jones
public class AnswersGUI
{
    //Declaring the variables for the swing components
    private JPanel answersPanel;
    private JLabel questionLabel;
    private JPanel answerButtonsPanel;
    private JButton answer1Button;
    private JButton answer2Button;
    private JButton answer3Button;
    private JButton answer4Button;
    private JPanel helpButtonsPanel;
    private JButton askThePublicButton;
    private JButton halfAndHalfButton;
    private JButton giveUsAClueButton;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    AnswersGUI(GameController gameController, String category)
    {
        PlayersController playersController = gameController.getPlayersController();
        UIController uiController = gameController.getUiController();
        QuestionsController questionsController = gameController.getQuestionsController();
        SoundController soundController = gameController.getSoundController();
        RandomController randomController = gameController.getRandomController();
        int currentPlayerIndex = gameController.getCurrentPlayerIndex();
        int currentQuestionNumber = gameController.getCurrentQuestionNumber();

        uiController.changeTitle("Zillionaire - Q" + currentQuestionNumber);

        soundController.PlaySound("question_music", true, false);

        //The 'ArrayList' 'questionAndAnswers' is created and populated using 'questionsController'
        ArrayList<String> questionAndAnswers = questionsController.getQuestion(category, currentQuestionNumber);

        //Setting the text of 'questionLabel' to the question string in 'questionAndAnswers'
        questionLabel.setText(questionAndAnswers.get(0));

        //Populates the text of the answer Buttons with 'questionAndAnswers's answers (shuffled using randomController)
        uiController.populateQuestions(
                randomController,
                new ArrayList<>(Arrays.asList(
                        questionAndAnswers.get(1),
                        questionAndAnswers.get(2),
                        questionAndAnswers.get(3),
                        questionAndAnswers.get(4))),
                new JButton[]{answer1Button, answer2Button, answer3Button, answer4Button});
        //Populates the 'enabled' values of the help facility buttons using playersController and currentPlayerIndex
        uiController.populateHelpFacilities(
                playersController,
                currentPlayerIndex,
                askThePublicButton,
                halfAndHalfButton,
                giveUsAClueButton);

        //Ensuring 'questionLabel's text fits within the component
        uiController.shrinkFontToComponent(questionLabel, questionLabel.getText());

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(answersPanel);
        uiController.refreshFonts(answersPanel);

        /*Destroys any active help facility windows, disables all the buttons,
        and checks whether answer1Button matches the correct answer (and acts accordingly)*/
        answer1Button.addActionListener(e ->
        {
            gameController.checkHelpFacilities();
            uiController.disableComponents(new ArrayList<>(Arrays.asList(
                    answer1Button, answer2Button, answer3Button, answer4Button,
                    askThePublicButton, halfAndHalfButton, giveUsAClueButton)));
            gameController.checkAnswer(answer1Button, questionAndAnswers.get(1));
        });

        /*Destroys any active help facility windows, disables all the buttons,
        and checks whether answer2Button matches the correct answer (and acts accordingly)*/
        answer2Button.addActionListener(e ->
        {
            gameController.checkHelpFacilities();
            uiController.disableComponents(new ArrayList<>(Arrays.asList(
                    answer1Button, answer2Button, answer3Button, answer4Button,
                    askThePublicButton, halfAndHalfButton, giveUsAClueButton)));
            gameController.checkAnswer(answer2Button, questionAndAnswers.get(1));
        });

        /*Destroys any active help facility windows, disables all the buttons,
        and checks whether answer3Button matches the correct answer (and acts accordingly)*/
        answer3Button.addActionListener(e ->
        {
            gameController.checkHelpFacilities();
            uiController.disableComponents(new ArrayList<>(Arrays.asList(
                    answer1Button, answer2Button, answer3Button, answer4Button,
                    askThePublicButton, halfAndHalfButton, giveUsAClueButton)));
            gameController.checkAnswer(answer3Button, questionAndAnswers.get(1));
        });

        /*Destroys any active help facility windows, disables all the buttons,
        and checks whether answer4Button matches the correct answer (and acts accordingly)*/
        answer4Button.addActionListener(e ->
        {
            gameController.checkHelpFacilities();
            uiController.disableComponents(new ArrayList<>(Arrays.asList(
                    answer1Button, answer2Button, answer3Button, answer4Button,
                    askThePublicButton, halfAndHalfButton, giveUsAClueButton)));
            gameController.checkAnswer(answer4Button, questionAndAnswers.get(1));
        });

        /*Plays the 'select' sound, sets the "askpublic" help facility usage of the 'Player' at
        currentPlayerIndex to true, disables 'askThePublicButton', and creates a new 'AskPublicGUI'*/
        askThePublicButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            playersController.setPlayerHelpFacilityUsed(currentPlayerIndex, "askpublic", true);
            askThePublicButton.setEnabled(false);
            new AskPublicGUI(
                    gameController,
                    questionAndAnswers.get(1),
                    new ArrayList<>(Arrays.asList(answer1Button, answer2Button, answer3Button, answer4Button)));
        });

        /*Plays the 'select' sound, sets the "halfandhalf" help facility usage of the 'Player' at currentPlayerIndex
        to true, disables 'halfAndHalfButton', and disables two randomly selected 'incorrect' buttons*/
        halfAndHalfButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            playersController.setPlayerHelpFacilityUsed(currentPlayerIndex, "halfandhalf", true);
            halfAndHalfButton.setEnabled(false);
            gameController.halfAndHalf(
                    questionAndAnswers.get(1),
                    new ArrayList<>(Arrays.asList(answer1Button, answer2Button, answer3Button, answer4Button)));
        });

        /*Plays the 'select' sound, sets the "clue" help facility usage of the 'Player' at
        currentPlayerIndex to true, disables 'giveUsAClueButton', and creates a new 'ClueGUI'*/
        giveUsAClueButton.addActionListener(e ->
        {
            soundController.PlaySound("select", false, false);
            playersController.setPlayerHelpFacilityUsed(currentPlayerIndex, "clue", true);
            giveUsAClueButton.setEnabled(false);
            new ClueGUI(gameController, questionAndAnswers.get(5));
        });
    }


    //Getter for this GUI's panel
    JPanel getAnswersPanel()
    {
        return answersPanel;
    }
}