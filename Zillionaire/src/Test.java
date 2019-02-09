import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Created by Luke Dicken - Jones
public class Test
{
    private static void destroyAllWindows()
    {
        for (Frame window : Frame.getFrames())
        {
            window.dispose();
        }
    }

    private static void testOutput(String methodName, String className, Object expectedValue, Object actualValue)
    {
        if (expectedValue instanceof Object[] &&
                actualValue instanceof Object[] &&
                Arrays.equals((Object[]) expectedValue, (Object[]) actualValue))
        {
            System.out.println("The '" + methodName + "' method in '" + className + "' works as intended");
        }
        else if (expectedValue.equals(actualValue))
        {
            System.out.println("The '" + methodName + "' method in '" + className + "' works as intended");
        }
        else
        {
            System.out.println("The '" + methodName + "' method in '" + className + "' does not work as intended" +
                    "\nExpected value: " + expectedValue +
                    "\nActual value: " + actualValue);
        }
    }

    private static void runAllTests()
    {
        ArrayList<String> methodsToIgnore = new ArrayList<>(Arrays.asList(
                "destroyAllWindows",
                "testOutput",
                "runAllTests",
                "main"));
        for (Method method : Test.class.getDeclaredMethods())
        {
            String methodName = method.getName();
            for (int i = 0; i < methodsToIgnore.size(); i++)
            {
                if (method.getParameterCount() != 0 || methodName.equals(methodsToIgnore.get(i)))
                {
                    break;
                }
                else if (i == methodsToIgnore.size() - 1)
                {
                    try
                    {
                        method.invoke(method);
                        System.out.println();
                    }
                    catch (Exception ignored)
                    {
                        System.out.println("'" + methodName + "' could not be invoked\n");
                    }
                    destroyAllWindows();
                }
            }
        }
    }


    //<editor-fold defaultstate="collapsed" desc="GameController tests">
    private static void checkHelpFacilitiesTest()
    {
        int expectedValue = 0;
        int actualValue = 0;

        GameController gameController = new GameController();
        new AskPublicGUI(
                gameController,
                "",
                new ArrayList<>(Arrays.asList(new JButton(), new JButton(), new JButton(), new JButton())));
        new ClueGUI(gameController, "");

        gameController.checkHelpFacilities();

        for (Frame frame : Frame.getFrames())
        {
            if (frame.isShowing() && (frame.getTitle().equals("Ask the Public") || frame.getTitle().equals("Give us a Clue")))
            {
                actualValue++;
            }
        }

        testOutput("checkHelpFacilities", "GameController", expectedValue, actualValue);
    }

    private static void halfAndHalfTest()
    {
        halfAndHalfTest("Correct answer");
    }

    private static void halfAndHalfTest(String correctAnswerText)
    {
        int expectedValue = 2;
        int actualValue = 0;

        GameController gameController = new GameController();
        JFrame gameFrame = gameController.getUiController().getGameFrame();
        JPanel panel = new JPanel();
        JButton button1 = new JButton();
        button1.setText(correctAnswerText);
        panel.add(button1);
        JButton button2 = new JButton();
        panel.add(button2);
        JButton button3 = new JButton();
        panel.add(button3);
        JButton button4 = new JButton();
        panel.add(button4);
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        gameFrame.setContentPane(panel);

        gameController.halfAndHalf(correctAnswerText, buttons);

        for (JButton button : buttons)
        {
            if (!button.isEnabled())
            {
                actualValue++;
            }
        }

        testOutput("halfAndHalf", "GameController", expectedValue, actualValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="PlayersController tests">
    private static void movePlayerUpTest()
    {
        String expectedValue = "Player 2";
        String actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        players.addElement(new Player("Player 2"));
        JList playersList = new JList();
        playersList.setModel(players);
        playersList.setSelectedIndex(1);

        playersController.movePlayerUp(playersList);

        actualValue = playersController.getPlayerName(0);

        testOutput("movePlayerUp", "PlayersController", expectedValue, actualValue);
    }

    private static void movePlayerDownTest()
    {
        String expectedValue = "Player 1";
        String actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        players.addElement(new Player("Player 2"));
        JList playersList = new JList();
        playersList.setModel(players);
        playersList.setSelectedIndex(0);

        playersController.movePlayerDown(playersList);

        actualValue = playersController.getPlayerName(1);

        testOutput("movePlayerDown", "PlayersController", expectedValue, actualValue);
    }

    private static void getPlayersSizeTest()
    {
        int expectedValue = 1;
        int actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();

        actualValue = playersController.getPlayersSize();

        testOutput("getPlayersSize", "PlayersController", expectedValue, actualValue);
    }

    private static void addPlayerTest()
    {
        addPlayerTest("Player 2");
    }

    private static void addPlayerTest(String playerName)
    {
        String expectedValue = playerName;
        String actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();

        playersController.addPlayer(playerName);

        actualValue = ((Player) players.get(players.size() - 1)).getName();

        testOutput("addPlayer", "PlayersController", expectedValue, actualValue);
    }

    private static void removePlayerTest()
    {
        removePlayerTest("Player 2");
    }

    private static void removePlayerTest(String playerName)
    {
        String expectedValue = "Player 1";
        String actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        players.addElement(new Player(playerName));

        playersController.removePlayer(playerName);

        actualValue = ((Player) players.get(players.size() - 1)).getName();

        testOutput("removePlayer", "PlayersController", expectedValue, actualValue);
    }

    private static void getPlayerNameTest()
    {
        String expectedValue = "Player 1";
        String actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();

        actualValue = playersController.getPlayerName(0);

        testOutput("getPlayerName", "PlayersController", expectedValue, actualValue);
    }

    private static void getPlayerMoneyTest()
    {
        int expectedValue = 0;
        int actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();

        actualValue = playersController.getPlayerMoney(0);

        testOutput("getPlayerMoney", "PlayersController", expectedValue, actualValue);
    }

    private static void addPlayerMoneyTest()
    {
        addPlayerMoneyTest(100);
    }

    private static void addPlayerMoneyTest(int moneyAmount)
    {
        int expectedValue = moneyAmount;
        int actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();

        playersController.addPlayerMoney(0, moneyAmount);

        actualValue = ((Player) players.get(0)).getMoney();

        testOutput("addPlayerMoney", "PlayersController", expectedValue, actualValue);
    }

    private static void resetAllPlayerMoneyTest()
    {
        int expectedValue = 0;
        int actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        ((Player) players.get(0)).setMoney(100);
        players.addElement(new Player("Player 2"));
        ((Player) players.get(1)).setMoney(200);

        playersController.resetAllPlayerMoney();

        actualValue = ((Player) players.get(0)).getMoney() + ((Player) players.get(1)).getMoney();

        testOutput("resetAllPlayerMoney", "PlayersController", expectedValue, actualValue);
    }

    private static void getPlayerHelpFacilityUsedTest()
    {
        getPlayerHelpFacilityUsedTest(true);
    }

    private static void getPlayerHelpFacilityUsedTest(boolean helpFacilityUsed)
    {
        boolean expectedValue = helpFacilityUsed;
        boolean actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        ((Player) players.get(0)).setAskPublicUsed(helpFacilityUsed);

        actualValue = playersController.getPlayerHelpFacilityUsed(0, "askpublic");

        testOutput("getPlayerHelpFacilityUsed", "PlayersController", expectedValue, actualValue);
    }

    private static void setPlayerHelpFacilityUsedTest()
    {
        setPlayerHelpFacilityUsedTest(true);
    }

    private static void setPlayerHelpFacilityUsedTest(boolean helpFacilityUsed)
    {
        boolean expectedValue = helpFacilityUsed;
        boolean actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();

        playersController.setPlayerHelpFacilityUsed(0, "askpublic", helpFacilityUsed);

        actualValue = playersController.getPlayerHelpFacilityUsed(0, "askpublic");

        testOutput("setPlayerHelpFacilityUsed", "PlayersController", expectedValue, actualValue);
    }

    private static void resetAllPlayerHelpFacilityUsedTest()
    {
        boolean expectedValue = false;
        boolean actualValue;

        GameController gameController = new GameController();
        PlayersController playersController = gameController.getPlayersController();
        DefaultListModel players = playersController.getPlayers();
        ((Player) players.get(0)).setAskPublicUsed(true);
        ((Player) players.get(0)).setHalfAndHalfUsed(true);
        ((Player) players.get(0)).setClueUsed(true);

        playersController.resetAllPlayerHelpFacilityUsed();

        actualValue = 1 == ((((Player) players.get(0)).isAskPublicUsed() ? 1 : 0) +
                (((Player) players.get(0)).isHalfAndHalfUsed() ? 1 : 0) +
                (((Player) players.get(0)).isClueUsed() ? 1 : 0));

        testOutput("resetAllPlayerHelpFacilityUsed", "PlayersController", expectedValue, actualValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="QuestionsController tests">
    private static void getQuestionTest()
    {
        getQuestionTest("general", 1);
    }

    private static void getQuestionTest(String category, int questionNumber)
    {
        boolean expectedValue = true;
        boolean actualValue;

        GameController gameController = new GameController();
        QuestionsController questionsController = gameController.getQuestionsController();
        QuestionReader questionReader = new QuestionReader(
                "resources/questions/",
                new ArrayList<>(Arrays.asList("general", "sport", "countries")));

        actualValue = questionReader.Read(category, questionNumber).contains(
                questionsController.getQuestion(category, questionNumber));

        testOutput("getQuestion", "QuestionsController", expectedValue, actualValue);
    }

    private static void addQuestionTest()
    {
        addQuestionTest(
                "general",
                1,
                "Question",
                "Correct answer",
                "Incorrect answer",
                "Incorrect answer",
                "Incorrect answer",
                "Hint");
    }

    private static void addQuestionTest(
            String category, int questionNumber, String question, String correctAnswer,
            String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3, String hint)
    {
        boolean expectedValue = true;
        boolean actualValue;

        GameController gameController = new GameController();
        QuestionsController questionsController = gameController.getQuestionsController();
        String questionsPath = "resources/questions/";
        QuestionReader questionReader = new QuestionReader(
                questionsPath,
                new ArrayList<>(Arrays.asList("general", "sport", "countries")));
        QuestionWriter questionWriter = new QuestionWriter(questionsPath);
        SoundController soundController = gameController.getSoundController();
        ArrayList<ArrayList<String>> oldFileContents = questionReader.Read(category, questionNumber);

        questionWriter.Write(soundController, category, questionNumber, new ArrayList<>(Arrays.asList(
                correctAnswer,
                incorrectAnswer1,
                incorrectAnswer2,
                incorrectAnswer3,
                hint)));

        actualValue = questionReader.Read(category, questionNumber).contains(
                questionsController.getQuestion(category, questionNumber));

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(questionsPath + category.toLowerCase() + "/" + questionNumber));
            for (int i = 0; i < oldFileContents.size(); i++)
            {
                for (String string : oldFileContents.get(i))
                {
                    writer.write(string + "\n");
                    writer.flush();
                }
                if (i != oldFileContents.size() - 1)
                {
                    writer.write("\n");
                    writer.flush();
                }
            }
            writer.close();
        }
        catch (Exception ignored)
        {
            System.out.println("Attempt to revert the file " +
                    questionsPath + category + "/" + questionNumber +
                    " back to its original state failed");
        }

        testOutput("addQuestion", "QuestionsController", expectedValue, actualValue);
    }

    private static void questionNumberToMoneyTest()
    {
        questionNumberToMoneyTest(1);
    }

    private static void questionNumberToMoneyTest(int questionNumber)
    {
        int expectedValue = new ArrayList<>(Arrays.asList(
                100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000))
                .get(questionNumber - 1);
        int actualValue;

        GameController gameController = new GameController();
        QuestionsController questionsController = gameController.getQuestionsController();

        actualValue = questionsController.questionNumberToMoney(questionNumber);

        testOutput("questionNumberToMoney", "QuestionsController", expectedValue, actualValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RandomController tests">
    private static void getRandomNumberTest()
    {
        getRandomNumberTest(0, 100);
    }

    private static void getRandomNumberTest(int lowestNumber, int highestNumber)
    {
        boolean expectedValue = true;
        boolean actualValue;

        GameController gameController = new GameController();
        RandomController randomController = gameController.getRandomController();

        int randomNumber = randomController.getRandomNumber(lowestNumber, highestNumber);
        actualValue = lowestNumber <= randomNumber && highestNumber >= randomNumber;

        testOutput("getRandomNumber", "RandomController", expectedValue, actualValue);
    }

    private static void getRandomNumbersTest()
    {
        getRandomNumbersTest(100, 3);
    }

    private static void getRandomNumbersTest(int highestNumber, int numberOfNumbers)
    {
        boolean expectedValue = true;
        boolean actualValue = false;

        GameController gameController = new GameController();
        RandomController randomController = gameController.getRandomController();

        ArrayList<Integer> randomNumbers = randomController.getRandomNumbers(highestNumber, numberOfNumbers);
        actualValue = highestNumber == Arrays.stream(randomNumbers.stream().mapToInt(i -> i).toArray()).sum() && numberOfNumbers == randomNumbers.size();

        testOutput("getRandomNumbers", "RandomController", expectedValue, actualValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SoundController tests">
    private static void PlaySoundTest()
    {
        PlaySoundTest("select");
    }

    private static void PlaySoundTest(String name)
    {
        boolean expectedValue = false;
        boolean actualValue;

        GameController gameController = new GameController();
        SoundController soundController = gameController.getSoundController();

        soundController.PlaySound(name, false, false);

        actualValue = soundController.silent();

        soundController.endAll();

        testOutput("PlaySound", "SoundController", expectedValue, actualValue);
    }

    private static void endAllTest()
    {
        boolean expectedValue = true;
        boolean actualValue;

        GameController gameController = new GameController();
        SoundController soundController = gameController.getSoundController();
        soundController.PlaySound("select", false, false);

        soundController.endAll();

        actualValue = soundController.silent();

        testOutput("endAll", "SoundController", expectedValue, actualValue);
    }

    private static void silentTest()
    {
        boolean expectedValue = false;
        boolean actualValue;

        GameController gameController = new GameController();
        SoundController soundController = gameController.getSoundController();
        soundController.PlaySound("select", false, false);

        actualValue = soundController.silent();

        testOutput("silent", "SoundController", expectedValue, actualValue);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="UIController tests">
    private static void initialFrameSetupTest()
    {
        String expectedValue = "Zillionaire - Main Menu";
        String actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();

        uiController.initialFrameSetup(gameController);

        actualValue = gameFrame.getTitle();

        testOutput("initialFrameSetup", "UIController", expectedValue, actualValue);
    }

    private static void changeTitleTest()
    {
        changeTitleTest("This is a test");
    }

    private static void changeTitleTest(String title)
    {
        String expectedValue = title;
        String actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();

        uiController.changeTitle(title);

        actualValue = gameFrame.getTitle();

        testOutput("changeTitle", "UIController", expectedValue, actualValue);
    }

    private static void changeFramePanelTest()
    {
        changeFramePanelTest(Color.red, Color.green);
    }

    private static void changeFramePanelTest(Color oldColour, Color newColour)
    {
        Color expectedValue = newColour;
        Color actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel oldPanel = new JPanel();
        oldPanel.setBackground(oldColour);
        gameFrame.setContentPane(oldPanel);
        JPanel newPanel = new JPanel();
        newPanel.setBackground(newColour);

        uiController.changeFramePanel(newPanel);

        actualValue = gameFrame.getContentPane().getBackground();

        testOutput("changeFramePanel", "UIController", expectedValue, actualValue);
    }

    private static void replacePanelTest()
    {
        replacePanelTest(Color.red, Color.green);
    }

    private static void replacePanelTest(Color oldColour, Color newColour)
    {
        Color expectedValue = newColour;
        Color actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel parentPanel = new JPanel();
        JPanel oldPanel = new JPanel();
        oldPanel.setBackground(oldColour);
        parentPanel.add(oldPanel);
        gameFrame.setContentPane(parentPanel);
        JPanel newPanel = new JPanel();
        newPanel.setBackground(newColour);

        uiController.replacePanel(oldPanel, newPanel);

        actualValue = parentPanel.getComponent(0).getBackground();

        testOutput("replacePanel", "UIController", expectedValue, actualValue);
    }

    private static void refreshColoursTest()
    {
        refreshColoursTest(Color.red, Color.green);
    }

    private static void refreshColoursTest(Color oldColour, Color newColour)
    {
        Color[] expectedValues = {newColour, newColour};
        Color[] actualValues;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        panel.setForeground(oldColour);
        panel.setBackground(oldColour);
        gameFrame.setContentPane(panel);
        gameFrame.setForeground(newColour);
        gameFrame.setBackground(newColour);

        uiController.refreshColours(panel);

        actualValues = new Color[]{panel.getForeground(), panel.getBackground()};

        testOutput("refreshColours", "UIController", expectedValues, actualValues);
    }

    private static void refreshFontsTest()
    {
        refreshFontsTest(
                new Font("Arial", Font.PLAIN, 20),
                new Font("Arial Black", Font.PLAIN, 20));
    }

    private static void refreshFontsTest(Font oldFont, Font newFont)
    {
        Font expectedValue = newFont;
        Font actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        panel.setFont(oldFont);
        gameFrame.setContentPane(panel);
        gameFrame.setFont(newFont);

        uiController.refreshFonts(panel);

        actualValue = panel.getFont();

        testOutput("refreshFonts", "UIController", expectedValue, actualValue);
    }

    private static void disableComponentsTest()
    {
        boolean expectedValue = false;
        boolean actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        gameFrame.setContentPane(panel);

        uiController.disableComponents(new ArrayList<>(Collections.singletonList(panel)));

        actualValue = panel.isEnabled();

        testOutput("disableComponents", "UIController", expectedValue, actualValue);
    }

    private static void changeForegroundColourTest()
    {
        changeForegroundColourTest(Color.red, Color.yellow, Color.green);
    }

    private static void changeForegroundColourTest(Color oldColour, Color backgroundColour, Color newColour)
    {
        Color expectedValue = newColour;
        Color actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();
        JFrame gameFrame = uiController.getGameFrame();
        gameFrame.setForeground(oldColour);
        gameFrame.setBackground(backgroundColour);

        uiController.changeForegroundColour(soundController, newColour);

        actualValue = gameFrame.getForeground();

        testOutput("changeForegroundColour", "UIController", expectedValue, actualValue);
    }

    private static void changeBackgroundColourTest()
    {
        changeBackgroundColourTest(Color.red, Color.yellow, Color.green);
    }

    private static void changeBackgroundColourTest(Color oldColour, Color foregroundColour, Color newColour)
    {
        Color expectedValue = newColour;
        Color actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        SoundController soundController = gameController.getSoundController();
        JFrame gameFrame = uiController.getGameFrame();
        gameFrame.setBackground(oldColour);
        gameFrame.setForeground(foregroundColour);

        uiController.changeBackgroundColour(soundController, newColour);

        actualValue = gameFrame.getBackground();

        testOutput("changeBackgroundColour", "UIController", expectedValue, actualValue);
    }

    private static void shrinkFontToComponentTest()
    {
        shrinkFontToComponentTest("This is a test", 50, new Dimension(100, 50));
    }

    private static void shrinkFontToComponentTest(String testText, int oldTextSize, Dimension labelDimensions)
    {
        int expectedValue = 17;
        int actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        label.setText(testText);
        label.setPreferredSize(labelDimensions);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getFontName(), labelFont.getStyle(), oldTextSize));
        panel.add(label);
        gameFrame.setContentPane(panel);

        uiController.shrinkFontToComponent(label, label.getText());

        actualValue = label.getFont().getSize();

        testOutput("shrinkFontToComponent", "UIController", expectedValue, actualValue);
    }

    private static void formatTextAreaTest()
    {
        formatTextAreaTest("This is\na test");
    }

    private static void formatTextAreaTest(String text)
    {
        int expectedValue = text.replaceAll("[^\n]", "").length() + 1;
        int actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel(new GridBagLayout());
        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        panel.add(textArea);
        gameFrame.setContentPane(panel);

        uiController.formatTextArea(textArea, false);

        actualValue = ((JPanel) panel.getComponent(0)).getComponents().length;

        testOutput("formatTextArea", "UIController", expectedValue, actualValue);
    }

    private static void populateQuestionPanelTest()
    {
        String[] expectedValues = {"Player 1", "£0", "£100"};
        String[] actualValues;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel();
        panel.add(nameLabel);
        JLabel moneyLabel = new JLabel();
        panel.add(moneyLabel);
        JProgressBar questionsProgressBar = new JProgressBar();
        panel.add(questionsProgressBar);
        gameFrame.setContentPane(panel);

        uiController.populateQuestionPanel(gameController, nameLabel, moneyLabel, questionsProgressBar);

        actualValues = new String[]{nameLabel.getText(), moneyLabel.getText(), questionsProgressBar.getString()};

        testOutput("populateQuestionPanel", "UIController", expectedValues, actualValues);
    }

    private static void populateQuestionsTest()
    {
        populateQuestionsTest(new String[]{"Answer 1", "Answer 2", "Answer 3", "Answer 4"});
    }

    private static void populateQuestionsTest(String[] potentialAnswers)
    {
        String[] expectedValues = new String[]{
                potentialAnswers[0],
                potentialAnswers[1],
                potentialAnswers[2],
                potentialAnswers[3]};
        String[] actualValues;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        RandomController randomController = gameController.getRandomController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JButton button1 = new JButton();
        panel.add(button1);
        JButton button2 = new JButton();
        panel.add(button2);
        JButton button3 = new JButton();
        panel.add(button3);
        JButton button4 = new JButton();
        panel.add(button4);
        gameFrame.setContentPane(panel);

        uiController.populateQuestions(
                randomController,
                new ArrayList<>(Arrays.asList(potentialAnswers)),
                new JButton[]{button1, button2, button3, button4});

        actualValues = new String[]{button1.getText(), button2.getText(), button3.getText(), button4.getText()};
        if (Arrays.asList(actualValues).contains(expectedValues[0]) &&
                Arrays.asList(actualValues).contains(expectedValues[1]) &&
                Arrays.asList(actualValues).contains(expectedValues[2]) &&
                Arrays.asList(actualValues).contains(expectedValues[3]))
        {
            expectedValues = new String[]{actualValues[0], actualValues[1], actualValues[2], actualValues[3]};
        }

        testOutput("populateQuestions", "UIController", expectedValues, actualValues);
    }

    private static void populatePublicBarsTest()
    {
        populatePublicBarsTest(
                1,
                "Correct answer",
                "Incorrect answer",
                false,
                false);
    }

    private static void populatePublicBarsTest(
            int questionNumber,
            String correctAnswerText,
            String incorrectAnswerText,
            boolean isHalfAndHalfUsed,
            boolean isClueUsed)
    {
        ArrayList<Object> expectedValues = new ArrayList<>(Arrays.asList(
                correctAnswerText,
                incorrectAnswerText,
                incorrectAnswerText,
                incorrectAnswerText));
        ArrayList<Object> actualValues;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        RandomController randomController = gameController.getRandomController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JTextArea textArea1 = new JTextArea();
        panel.add(textArea1);
        JTextArea textArea2 = new JTextArea();
        panel.add(textArea2);
        JTextArea textArea3 = new JTextArea();
        panel.add(textArea3);
        JTextArea textArea4 = new JTextArea();
        panel.add(textArea4);
        JButton button1 = new JButton();
        button1.setText(correctAnswerText);
        panel.add(button1);
        JButton button2 = new JButton();
        button2.setText(incorrectAnswerText);
        panel.add(button2);
        JButton button3 = new JButton();
        button3.setText(incorrectAnswerText);
        panel.add(button3);
        JButton button4 = new JButton();
        button4.setText(incorrectAnswerText);
        panel.add(button4);
        JProgressBar progressBar1 = new JProgressBar();
        panel.add(progressBar1);
        JProgressBar progressBar2 = new JProgressBar();
        panel.add(progressBar2);
        JProgressBar progressBar3 = new JProgressBar();
        panel.add(progressBar3);
        JProgressBar progressBar4 = new JProgressBar();
        panel.add(progressBar4);
        gameFrame.setContentPane(panel);

        uiController.populatePublicBars(
                randomController,
                questionNumber,
                new ArrayList<>(Arrays.asList(textArea1, textArea2, textArea3, textArea4)),
                new ArrayList<>(Arrays.asList(button1, button2, button3, button4)),
                correctAnswerText,
                new ArrayList<>(Arrays.asList(progressBar1, progressBar2, progressBar3, progressBar4)),
                isHalfAndHalfUsed,
                isClueUsed);

        actualValues = new ArrayList<>(Arrays.asList(
                textArea1.getText(),
                textArea2.getText(),
                textArea3.getText(),
                textArea4.getText()));

        testOutput("populatePublicBars", "UIController", expectedValues, actualValues);
    }

    private static void populateHelpFacilitiesTest()
    {
        populateHelpFacilitiesTest(
                true,
                false,
                true);
    }

    private static void populateHelpFacilitiesTest(
            boolean isAskPublicUsed,
            boolean isHalfAndHalfUsed,
            boolean isClueUsed)
    {
        ArrayList<Boolean> expectedValues = new ArrayList<>(Arrays.asList(
                !isAskPublicUsed,
                !isHalfAndHalfUsed,
                !isClueUsed));
        ArrayList<Boolean> actualValues;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        PlayersController playersController = gameController.getPlayersController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JButton askThePublicButton = new JButton();
        panel.add(askThePublicButton);
        JButton halfAndHalfButton = new JButton();
        panel.add(halfAndHalfButton);
        JButton giveUsAClueButton = new JButton();
        panel.add(giveUsAClueButton);
        gameFrame.setContentPane(panel);
        Player player = playersController.getPlayers().get(0);
        player.setAskPublicUsed(isAskPublicUsed);
        player.setHalfAndHalfUsed(isHalfAndHalfUsed);
        player.setClueUsed(isClueUsed);

        uiController.populateHelpFacilities(
                playersController,
                0,
                askThePublicButton,
                halfAndHalfButton,
                giveUsAClueButton);

        actualValues = new ArrayList<>(Arrays.asList(
                askThePublicButton.isEnabled(),
                halfAndHalfButton.isEnabled(),
                giveUsAClueButton.isEnabled()));

        testOutput("populateHelpFacilities", "UIController", expectedValues, actualValues);
    }

    private static void populateLeaderboardTest()
    {
        String expectedValue = "Commiserations 'Player 1'. You won nothing.";
        String actualValue;

        GameController gameController = new GameController();
        UIController uiController = gameController.getUiController();
        PlayersController playersController = gameController.getPlayersController();
        JFrame gameFrame = uiController.getGameFrame();
        JPanel panel = new JPanel();
        JLabel winnersLabel = new JLabel();
        panel.add(winnersLabel);
        JScrollPane leaderboardScrollPane = new JScrollPane();
        panel.add(leaderboardScrollPane);
        JTextArea leaderboardTextArea = new JTextArea();
        panel.add(leaderboardTextArea);
        gameFrame.setContentPane(panel);

        uiController.populateLeaderboard(
                playersController,
                panel,
                winnersLabel,
                leaderboardScrollPane,
                leaderboardTextArea);

        actualValue = winnersLabel.getText();

        testOutput("populateLeaderboard", "UIController", expectedValue, actualValue);
    }
    //</editor-fold>


    public static void main(String[] args)
    {
        runAllTests();
    }
}