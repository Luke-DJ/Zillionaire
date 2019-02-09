import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Created by Luke Dicken - Jones
public class GameController
{
    //Declaring instance variables for the other controllers
    private PlayersController playersController;
    private UIController uiController;
    private RandomController randomController;
    private QuestionsController questionsController;
    private SoundController soundController;
    //Declaring instance variables for the current player index/question number
    private int currentPlayerIndex;
    private int currentQuestionNumber;
    //Declaring and instantiating the variables for the colours used to show whether an answer is correct or incorrect
    private Color correctAnswerColour = Color.green;
    private Color incorrectAnswerColour = Color.red;
    /*Declaring and instantiating the variable for the number of
    seconds until the game moves on after a question has been answered*/
    private float answerDelay = 1.5f;


    //Constructor - instantiating the class's instance variables
    public GameController()
    {
        this.soundController = new SoundController();
        this.playersController = new PlayersController(soundController);
        this.uiController = new UIController();
        this.randomController = new RandomController();
        this.questionsController = new QuestionsController(soundController, randomController);

        this.playersController.addPlayer("Player 1");
        this.uiController.initialFrameSetup(this);

        this.currentPlayerIndex = 0;
        this.currentQuestionNumber = 1;
    }


    //<editor-fold defaultstate="collapsed" desc="Checks whether the player's answer matches the correct answer, and acts accordingly">
    /*If they match,
        the player is credited with the amount of money that the question is worth,
        and the game moves on to the next question
    If they don't match,
        the player's turn is ended
    'correct'/'incorrect' sounds are played, and the colour of the button
    is changed according to whether the player answered correctly/incorrectly
    Also performs additional checks to see if the game has ended or if the player has answered all fifteen questions
    The game doesn't move on until after a delay*/
    //</editor-fold>
    void checkAnswer(JButton playerAnswerButton, String actualAnswer)
    {
        Timer timer;
        if (playerAnswerButton.getText().equals(actualAnswer))
        {
            soundController.PlaySound("correct", false, true);

            uiController.flashingButton(playerAnswerButton, correctAnswerColour, answerDelay, 5);

            ActionListener correctAnswer = evt ->
            {
                playersController.addPlayerMoney(
                        currentPlayerIndex,
                        questionsController.questionNumberToMoney(currentQuestionNumber) -
                                playersController.getPlayerMoney(currentPlayerIndex));
                if (checkGameOver(true))
                {
                    endGame();
                }
                else if (checkLastQuestion())
                {
                    currentPlayerIndex++;
                    currentQuestionNumber = 1;
                    uiController.changeFramePanel(new PlayerIntroGUI(getGameController()).getPlayerIntroPanel());
                }
                else
                {
                    currentQuestionNumber++;
                    uiController.changeFramePanel(new QuestionGUI(
                            getGameController(),
                            new CategoryGUI(getGameController()).getCategoryPanel()).getQuestionPanel());
                }
            };
            timer = new Timer(Math.round(answerDelay * 1000), correctAnswer);
        }
        else
        {
            soundController.PlaySound("incorrect", false, true);

            uiController.flashingButton(playerAnswerButton, incorrectAnswerColour, answerDelay, 5);

            ActionListener incorrectAnswer = evt ->
            {
                if (checkGameOver(false))
                {
                    endGame();
                }
                else
                {
                    currentPlayerIndex++;
                    currentQuestionNumber = 1;
                    uiController.changeFramePanel(new PlayerIntroGUI(this).getPlayerIntroPanel());
                }
            };
            timer = new Timer(Math.round(answerDelay * 1000), incorrectAnswer);
        }
        timer.setRepeats(false);
        timer.start();
    }

    //Searches for active help facility windows (using their titles), and 'disposes' (destroys) any that are found
    void checkHelpFacilities()
    {
        for (Frame frame : Frame.getFrames())
        {
            if (frame.getTitle().equals("Ask the Public") || frame.getTitle().equals("Give us a Clue"))
            {
                frame.dispose();
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Disables two randomly selected buttons, out of an array of all four buttons, that display incorrect answers">
        /*Populates a new 'ArrayList' of all four buttons
        Removes the button displaying the correct answer from the 'ArrayList'
        Removes a random button from the remaining three buttons in the 'ArrayList'
        Disables the remaining two buttons in the 'ArrayList'*/
    //</editor-fold>
    void halfAndHalf(String correctAnswer, ArrayList<JButton> buttons)
    {
        ArrayList<JButton> disabledButtons = new ArrayList<>(buttons);
        for (JButton button : disabledButtons)
        {
            if (button.getText().equals(correctAnswer))
            {
                disabledButtons.remove(button);
                break;
            }
        }
        disabledButtons.remove(randomController.getRandomNumber(0, 2));
        uiController.disableComponents(new ArrayList<>(disabledButtons));
    }

    //<editor-fold defaultstate="collapsed" desc="Returns true if the current state of the game means that it should end">
        /*(if the given answer is incorrect,
        and the current player is the last player
        or
        if the given answer is correct,
        the current player is the last player,
        and the current question is the last question)*/
    //</editor-fold>
    private boolean checkGameOver(boolean correctAnswer)
    {
        return (!correctAnswer && checkLastPlayer()) || (correctAnswer && checkLastPlayer() && checkLastQuestion());
    }

    //<editor-fold defaultstate="collapsed" desc="Returns true if the current player is the last player">
    //(if the current player's index position is the final index position of the list of players (size - 1))
    //</editor-fold>
    private boolean checkLastPlayer()
    {
        return currentPlayerIndex == playersController.getPlayersSize() - 1;
    }

    //Returns true if the current question number is equal to fifteen (the total number of questions)
    private boolean checkLastQuestion()
    {
        return currentQuestionNumber == 15;
    }

    //<editor-fold defaultstate="collapsed" desc="Changes the current screen to the end screen, and resets all necessary values so that the game can be played again">
        /*Changes the current screen to the end screen, and resets all necessary values so that the game can be played again
        Changes the frame panel to a new 'EndGUI' panel
        Sets the current player index to 0 (first player)
        Sets the current question number to 1 (first question)
        Resets the money of all players
        Resets the help facility usage of all players
        Clears all 'used' questions*/
    //</editor-fold>
    private void endGame()
    {
        uiController.changeFramePanel(new EndGUI(this).getEndPanel());
        currentPlayerIndex = 0;
        currentQuestionNumber = 1;
        playersController.resetAllPlayerMoney();
        playersController.resetAllPlayerHelpFacilityUsed();
        questionsController.clearUsedQuestions();
    }

    //Exits the program
    void exitGame()
    {
        System.exit(0);
    }


    //Getters and setters:

    GameController getGameController()
    {
        return this;
    }

    PlayersController getPlayersController()
    {
        return playersController;
    }

    public UIController getUiController()
    {
        return uiController;
    }

    QuestionsController getQuestionsController()
    {
        return questionsController;
    }

    RandomController getRandomController()
    {
        return randomController;
    }

    public SoundController getSoundController()
    {
        return soundController;
    }

    int getCurrentPlayerIndex()
    {
        return currentPlayerIndex;
    }

    int getCurrentQuestionNumber()
    {
        return currentQuestionNumber;
    }
}