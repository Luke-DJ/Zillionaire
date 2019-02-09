import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Created by Luke Dicken - Jones
public class UIController
{
    //Declaring the variable for the game frame
    private JFrame gameFrame;
    //Declaring and instantiating the variable for the preferred fonts that the program will try to use
    private ArrayList<String> preferredFonts = new ArrayList<>(Arrays.asList("Berlin Sans FB Demi", "Arial Black"));
    //Declaring and instantiating the variable for the name of the font that the program will use
    private String fontName = new JLabel().getFont().getFontName();
    //Declaring and instantiating the variable for the path to the 'images' folder
    private String imagesPath = "resources/images/";
    /*Declaring and instantiating the variable for the 'ArrayList'
    containing the public's highest certainty value for each question*/
    private ArrayList<Integer> publicHighestCertainty = new ArrayList<>(Arrays.asList(
            100, 98, 96, 94, 92, 90, 88, 86, 84, 82, 80, 78, 76, 74, 72));
    /*Declaring and instantiating the variable for the increase
    in the public's certainty when 'Give us a Clue' has been used*/
    private float publicCertaintyIncrease = 0.25f;
    /*Declaring and instantiating the variable for the 'ArrayList'
    containing the chance of the public being incorrect for each question*/
    private ArrayList<Float> publicIncorrectChances = new ArrayList<>(Arrays.asList(
            1.5f, 4.5f, 7.5f, 10.5f, 13.5f, 16.5f, 19.5f, 22.5f, 25.5f, 28.5f, 31.5f, 34.5f, 37.5f, 40.5f, 43.5f));
    /*Declaring and instantiating the variable for the decrease in the chance
    of the public being incorrect when 'Give us a Clue' has been used*/
    private float publicIncorrectChanceDecrease = 0.5f;


    //Constructor - instantiating the class's instance variables
    public UIController()
    {
        this.gameFrame = new JFrame();

        //<editor-fold defaultstate="collapsed" desc="'fontName' is tried to be set to the first font name in 'preferredFonts' that's available on the user's system">
            /*The 'ArrayList' 'availableFonts' is created and populated with the available font names on the user's system
            'preferredFonts' is iterated over, and if 'availableFonts' contains
            a 'preferredFont', the 'preferredFont' is assigned to 'fontName'*/
        //</editor-fold>
        ArrayList<String> availableFonts = new ArrayList<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String preferredFont : preferredFonts)
        {
            if (availableFonts.contains(preferredFont))
            {
                fontName = preferredFont;
                break;
            }
        }
    }


    //<editor-fold defaultstate="collapsed" desc="Performs the initial setup of 'gameFrame'">
        /*The default look and feel is tried to be set to that of the user's system
        The default text colour of ProgressBars is set to black (not covered by the bar) and white (covered by the bar)
        The default close operation of 'gameFrame' is set so that the
        program terminates when the window's 'x' button is clicked
        'gameFrame' is set to not be resizable
        'gameFrame's foreground is set to black
        'gameFrame's background is set to white
        'gameFrame's font is set to 'fontName' of size twenty
        'gameFrame's icon is set to 'icon.png' in 'imagesPath'
        'gameFrame's cursor is set to 'cursor.png' in 'imagesPath'
        'gameFrame's title is set to 'Zillionaire'
        'gameFrame's panel is set to a new 'MainMenuGUI' panel
        'gameFrame' is packed and 'gameFrame' is made visible*/
    //</editor-fold>
    void initialFrameSetup(GameController gameController)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ignored)
        {
        }
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        UIManager.put("ProgressBar.selectionBackground", Color.white);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setForeground(Color.black);
        gameFrame.setBackground(Color.white);
        gameFrame.setFont(new Font(fontName, Font.PLAIN, 20));
        gameFrame.setIconImage(new ImageIcon(imagesPath + "icon.png").getImage());
        gameFrame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new ImageIcon(imagesPath + "cursor.png").getImage()
                        .getScaledInstance(32, 32, Image.SCALE_DEFAULT),
                new Point(0, 0),
                "Cursor"));

        changeTitle("Zillionaire");
        changeFramePanel(new MainMenuGUI(gameController).getMainMenuPanel());

        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    //The title of 'gameFrame' is set to a given string
    public void changeTitle(String title)
    {
        gameFrame.setTitle(title);
    }

    //'gameFrame's panel is set to a given panel and refreshes 'gameFrame'
    void changeFramePanel(JPanel newPanel)
    {
        gameFrame.setContentPane(newPanel);
        refreshFrame();
    }

    //<editor-fold defaultstate="collapsed" desc="Replaces a given panel with another given panel">
        /*The parent of 'currentPanel' is stored as 'parentPanel'
        'currentPanel' is removed from 'parentPanel'
        'newPanel' is added to 'parentPanel'
        'gameFrame' is refreshed*/
    //</editor-fold>
    void replacePanel(JPanel currentPanel, JPanel newPanel)
    {
        Container parentPanel = currentPanel.getParent();
        parentPanel.remove(currentPanel);
        parentPanel.add(newPanel);
        refreshFrame();
    }

    //'revalidate's 'gameFrame'
    private void refreshFrame()
    {
        gameFrame.revalidate();
    }

    //<editor-fold defaultstate="collapsed" desc="Changes the foreground and background colours of the components in a given Panel to those of 'gameFrame'">
        /*'gameFrame's foreground colour is stored as 'foreground'
        'gameFrame's background colour is stored as 'background'
        The given panel's foreground colour is set to 'foreground'
        The given panel's background colour is set to 'background'
        The default panel background colour is set to 'background'
        The default OptionPane background colour is set to 'background'
        The default OptionPane message foreground colour is set to 'foreground'
        The given panel's components are iterated over
            If the current component isn't a ProgressBar
                If the current component is a Panel or a ScrollPane
                    'refreshColours' is called again, using the current component
                If the current component is a Separator
                    The foreground and background of the component are set to 'background'
                Otherwise,
                    If the component's foreground colour isn't already 'foreground',
                    the component's foreground colour is set to 'foreground'
                    If the component's background colour isn't already 'background',
                    the component's background colour is set to 'background'*/
    //</editor-fold>
    public void refreshColours(JComponent panel)
    {
        Color foreground = gameFrame.getForeground();
        Color background = gameFrame.getBackground();

        panel.setForeground(foreground);
        panel.setBackground(background);
        UIManager.put("Panel.background", background);
        UIManager.put("OptionPane.background", background);
        UIManager.put("OptionPane.messageForeground", foreground);
        for (Component component : panel.getComponents())
        {
            if (!(component instanceof JProgressBar))
            {
                if (component instanceof JPanel)
                {
                    refreshColours((JPanel) component);
                }
                else if (component instanceof JScrollPane)
                {
                    refreshColours(((JScrollPane) component).getViewport());
                }
                else if (component instanceof JSeparator)
                {
                    component.setForeground(background);
                    component.setBackground(background);
                }
                else
                {
                    if (!component.getForeground().equals(foreground))
                    {
                        component.setForeground(foreground);
                    }
                    if (!component.getBackground().equals(background))
                    {
                        component.setBackground(background);
                    }
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Changes the font of the components in a given Panel to that of 'gameFrame'">
        /*'gameFrame's font name is stored as 'fontName'
        The given panel's font name is set to 'fontName'
        The default OptionPane message font is set to 'fontName' of size twenty
        The default OptionPane button font is set to 'fontName' of size fifteen
        The given panel's components are iterated
            The font name of the current component is tried to be set to 'fontName'
            If the current component is a Panel or a ScrollPane
                'refreshFonts' is called again, using the current component*/
    //</editor-fold>
    public void refreshFonts(JComponent panel)
    {
        String fontName = gameFrame.getFont().getFontName();

        panel.setFont(new Font(fontName, panel.getFont().getStyle(), panel.getFont().getSize()));
        UIManager.put("OptionPane.messageFont", new Font(fontName, Font.PLAIN, 20));
        UIManager.put("OptionPane.buttonFont", new Font(fontName, Font.PLAIN, 15));
        for (Component component : panel.getComponents())
        {
            try
            {
                component.setFont(new Font(fontName, component.getFont().getStyle(), component.getFont().getSize()));
            }
            catch (Exception ignored)
            {
            }
            if (component instanceof JPanel)
            {
                refreshFonts((JPanel) component);
            }
            else if (component instanceof JScrollPane)
            {
                refreshFonts(((JScrollPane) component).getViewport());
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Disables all components in a given 'ArrayList'">
        /*Iterates over the given 'ArrayList'
            'enabled' of the current component is set to false*/
    //</editor-fold>
    void disableComponents(ArrayList<JComponent> components)
    {
        for (JComponent component : components)
        {
            component.setEnabled(false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="'gameFrame's foreground and background colours are changed to given foreground and background colours">
        /*If neither of the given colours are 'null'
            If the given foreground colour and background colour aren't the same
                'gameFrame's foreground and background colours are set to the given foreground and background colours,
                and true is returned
            Otherwise, an error popup is displayed, and false is returned*/
    //</editor-fold>
    private boolean changeColourScheme(Color foregroundColour, Color backgroundColour)
    {
        if (foregroundColour != null && backgroundColour != null)
        {
            if (!foregroundColour.equals(backgroundColour))
            {
                gameFrame.setForeground(foregroundColour);
                gameFrame.setBackground(backgroundColour);
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Foreground colour and background colour cannot be the same",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        return false;
    }

    /*Calls 'changeColourScheme' with a given foreground colour and 'gameFrame's background colour
    Appropriate sounds are played according to whether or not the foreground colour is changed successfully*/
    void changeForegroundColour(SoundController soundController, Color foregroundColour)
    {
        if (changeColourScheme(foregroundColour, gameFrame.getBackground()))
        {
            soundController.PlaySound("correct", false, false);
        }
        else
        {
            soundController.PlaySound("incorrect", false, false);
        }
    }

    /*Calls 'changeColourScheme' with 'gameFrame's foreground colour and a given background colour
    Appropriate sounds are played according to whether or not the background colour is changed successfully*/
    void changeBackgroundColour(SoundController soundController, Color backgroundColour)
    {
        if (changeColourScheme(gameFrame.getForeground(), backgroundColour))
        {
            soundController.PlaySound("correct", false, false);
        }
        else
        {
            soundController.PlaySound("incorrect", false, false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="'Flashes' a given button, using a given colour, a given number of times, over a given amount of time">
        /*The 'Timer' 'timer' is created
        'oldColour' is set to the background colour of the given button
        The given 'numberOfFlashes' is doubled (as each 'flash' is two actions,
        changing the old button colour to the new one, and then changing it back again)
        'flashMilliseconds' (how long an individual flash should last) is set to the
        given 'flashingLength' divided by the given 'numberOfFlashes', multiplied by
        one thousand to convert it to milliseconds, and rounded to ensure it's an integer
        The 'ActionListener' 'flash' is created
            If the given button's background colour is the same as 'newColour',
            then the given button's background colour is set to 'oldColour'
            Otherwise, the given button's background colour is set to 'newColour'
        'delay' is set to zero, and the given 'numberOfFlashes' is iterated over
            A new 'Timer' is assigned to 'timer', using 'flashMilliseconds', and the 'ActionListener' 'flash'
            'timer' is set to not repeat itself, its initial delay is set to 'delay', and it is started
            'flashMilliseconds' is added to 'delay'*/
    //</editor-fold>
    void flashingButton(JButton button, Color newColour, float flashingLength, float numberOfFlashes)
    {
        Timer timer;
        Color oldColour = new Color(button.getBackground().getRGB());
        numberOfFlashes *= 2;
        int flashMilliseconds = Math.round((flashingLength / numberOfFlashes) * 1000);

        ActionListener flash = evt ->
        {
            if (button.getBackground().equals(newColour))
            {
                button.setBackground(oldColour);
            }
            else
            {
                button.setBackground(newColour);
            }
        };

        int delay = 0;
        for (int i = 0; i < numberOfFlashes; i++)
        {
            timer = new Timer(flashMilliseconds, flash);
            timer.setRepeats(false);
            timer.setInitialDelay(delay);
            timer.start();
            delay += flashMilliseconds;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Shrinks the size of the text of a given component down to the length of a given string (that uses the font of the...">
    //...given component)
        /*'componentWidth' is set to the preferred width of the given component
        'fontMetrics' is set to the 'FontMetrics' of the given component's font
        If 'componentWidth' is greater than/equal to the width of the given string
        (using the font of the given component), then the method is terminated
        Otherwise, while 'componentWidth' is less than the width
        of the given string (using the font of the given component)
            'font' is set to the font of the given component
            The given component's font is set to a new 'Font', using
            'font's font name, 'font's style, and 'font's size minus one
            'fontMetrics' is set to the 'FontMetrics' of the given component's font*/
    //</editor-fold>
    void shrinkFontToComponent(JComponent component, String textToShrinkTo)
    {
        int componentWidth = component.getPreferredSize().width;
        FontMetrics fontMetrics = component.getFontMetrics(component.getFont());

        if (componentWidth >= fontMetrics.stringWidth(textToShrinkTo))
        {
            return;
        }
        while (componentWidth < fontMetrics.stringWidth(textToShrinkTo))
        {
            Font font = component.getFont();
            component.setFont(new Font(font.getFontName(), font.getStyle(), font.getSize() - 1));
            fontMetrics = component.getFontMetrics(component.getFont());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Replaces a given TextArea with a Panel containing Labels that resembles a centre-aligned version of the given...">
    //...TextArea, with a given boolean that defines where to split the given TextArea's text
        /*The 'ArrayList' 'rawTextArray' is created
        If the given boolean is true, the given TextArea is split at each space and is stored in 'rawTextArray'
        Otherwise, the given TextArea is split at each newline and is stored in 'rawTextArray'

        'fontMetrics' is set to the 'FontMetrics' of the given TextArea's font
        The string 'longestString' is created
        'rawTextArray' is iterated over, and if the length of 'longestString' (using
        the font of the given TextArea) is smaller than the length of the current string
        (using the font of the given TextArea), 'longestString' is set to the current string
        'shrinkFontToComponent' is called using the given TextArea and 'longestString'

        The 'ArrayList' of 'ArrayList's 'rawTextArray' is created
        If the given boolean is true
            'width' is set to the preferred width of the given TextArea
            'fontMetrics' is set to the 'FontMetrics' of the given TextArea's font
            A new 'ArrayList' is added to 'groupedTextArray'
            'rawTextArray' is iterated over
                'currentTextArray' is set to a reference of the 'ArrayList' in 'groupedTextArray's last position
                If 'width' is less than the width of the strings in 'currentTextArray'
                plus the current string (using the font of the given TextArea)
                    The current string is added to 'groupedTextArray' as an 'ArrayList'
                Otherwise, the current string is added to 'currentTextArray'
                (the 'ArrayList' in 'groupedTextArray's last position)
        Otherwise, each string in 'rawTextArray' is added to 'groupedTextArray' as an 'ArrayList'

        'parent' is set to the given TextArea's parent container
        'background' is set to the given TextArea's background, 'font' is set to the
        given TextArea's font, and 'size' is set to the given 'TextArea's preferred size
        'newPanel' is set to a new Panel of type 'GridBagLayout'
        'newPanel's background is set to 'background', and 'newPanel's size is set to 'size'
        'gridBagConstraints' is set to the given TextArea's constraints in 'parent's layout
        The given TextArea is removed from 'parent', and 'newPanel' is added to 'parent' with 'gridBagConstraints'
        'gridBagConstraints' is set to a new 'GridBagConstraints'
        'groupedTextArray' is iterated over
            'lineLabel' is set to a new Label
            'lineLabel's background is set to 'background', lineLabel's font is set to 'font,
            'lineLabel's horizontal and vertical alignments and text positions are set to 'CENTER',
            and 'lineLabel's text is set to the strings in the current 'ArrayList'
            The 'gridy' of 'gridBagConstraints' is set to the integer of the current iteration
            'lineLabel' is added to 'newPanel' with 'gridBagConstraints'

        'gameFrame' is refreshed*/
    //</editor-fold>
    void formatTextArea(JTextArea textArea, boolean oneLine)
    {
        ArrayList<String> rawTextArray;
        if (oneLine)
        {
            rawTextArray = new ArrayList<>(Arrays.asList(textArea.getText().split(" ")));
        }
        else
        {
            rawTextArray = new ArrayList<>(Arrays.asList(textArea.getText().split("\n")));
        }

        FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());
        String longestString = "";
        for (String string : rawTextArray)
        {
            if (fontMetrics.stringWidth(longestString) < fontMetrics.stringWidth(string))
            {
                longestString = string;
            }
        }
        shrinkFontToComponent(textArea, longestString);

        ArrayList<ArrayList<String>> groupedTextArray = new ArrayList<>();
        if (oneLine)
        {
            int width = textArea.getPreferredSize().width;
            fontMetrics = textArea.getFontMetrics(textArea.getFont());
            groupedTextArray.add(new ArrayList<>());
            for (String string : rawTextArray)
            {
                ArrayList<String> currentTextArray = groupedTextArray.get(groupedTextArray.size() - 1);

                if (width < fontMetrics.stringWidth(String.join(" ", currentTextArray) + " " + string))
                {
                    groupedTextArray.add(new ArrayList<>(Collections.singletonList(string)));
                }
                else
                {
                    currentTextArray.add(string);
                }
            }
        }
        else
        {
            for (String string : rawTextArray)
            {
                groupedTextArray.add(new ArrayList<>(Collections.singletonList(string)));
            }
        }

        Container parent = textArea.getParent();

        Color background = textArea.getBackground();
        Font font = textArea.getFont();
        Dimension size = textArea.getPreferredSize();

        JPanel newPanel = new JPanel(new GridBagLayout());
        newPanel.setBackground(background);
        newPanel.setPreferredSize(size);

        GridBagConstraints gridBagConstraints = ((GridBagLayout) parent.getLayout()).getConstraints(textArea);
        parent.remove(textArea);
        parent.add(newPanel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        for (int i = 0; i < groupedTextArray.size(); i++)
        {
            JLabel lineLabel = new JLabel();
            lineLabel.setBackground(background);
            lineLabel.setFont(font);
            lineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lineLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            lineLabel.setVerticalAlignment(SwingConstants.CENTER);
            lineLabel.setVerticalTextPosition(SwingConstants.CENTER);
            lineLabel.setText(String.join(" ", groupedTextArray.get(i)));
            gridBagConstraints.gridy = i;
            newPanel.add(lineLabel, gridBagConstraints);
        }

        refreshFrame();
    }

    //<editor-fold defaultstate="collapsed" desc="Populates a given name Label, money Label and questions ProgressBar, with values managed by a given GameController">
        /*'playersController' is set to the given GameController's PlayersController,
        and 'questionsController' is set to the given GameController's QuestionsController
        'currentPlayerIndex' is set to the given GameController' current player index, and
        'currentQuestionNumber' is set to the given GameController's current question number
        The given 'nameLabel's text is set to the name of the player at 'currentPlayerIndex' using 'playersController',
        and the given 'moneyLabel's text is set to a '£' symbol followed by
        the money of a player at 'currentPlayerIndex' using 'playersController'
        The given 'questionsProgressBar's value is set to 'currentQuestionNumber', and the given 'questionsProgressBar's
        text is set to the amount of money 'currentQuestionNumber' is worth using 'questionsController'*/
    //</editor-fold>
    void populateQuestionPanel(
            GameController gameController, JLabel nameLabel, JLabel moneyLabel, JProgressBar questionsProgressBar)
    {
        PlayersController playersController = gameController.getPlayersController();
        QuestionsController questionsController = gameController.getQuestionsController();
        int currentPlayerIndex = gameController.getCurrentPlayerIndex();
        int currentQuestionNumber = gameController.getCurrentQuestionNumber();

        nameLabel.setText(playersController.getPlayerName(currentPlayerIndex));
        moneyLabel.setText("£" + playersController.getPlayerMoney(currentPlayerIndex));
        questionsProgressBar.setValue(currentQuestionNumber);
        questionsProgressBar.setString("£" + questionsController.questionNumberToMoney(currentQuestionNumber));
    }

    //<editor-fold defaultstate="collapsed" desc="Populates the text of a given array of Buttons with a given 'ArrayList' of answers that's shuffled using a given...">
    //...RandomController
        /*'shuffledAnswers' is set to a copy of the given 'answers'
        The given 'randomController' is used to shuffle 'shuffledAnswers'
        The text of each Button in the given 'buttons' array is set
        to the answer in 'shuffledAnswers' in the same index position*/
    //</editor-fold>
    void populateQuestions(RandomController randomController, ArrayList<String> answers, JButton[] buttons)
    {
        ArrayList<String> shuffledAnswers = new ArrayList<>(answers);
        randomController.shuffleArrayList(shuffledAnswers);
        for (int i = 0; i < 4; i++)
        {
            buttons[i].setText(shuffledAnswers.get(i));
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Populates the text of a given 'ArrayList' of TextAreas with the text of a given 'ArrayList' of Buttons, and...">
    /*...populates the values of a given 'ArrayList' of ProgressBars with
    random values (based on certain factors) using a given RandomController*/
        /*The text of each TextArea in the given 'ArrayList' of TextAreas is set to the
        text of the button in the given 'ArrayList' of buttons in the same index position

        The 'ArrayList' 'barValues' is created along with the 'publicCorrect' boolean
        If 'isClueUsed' is true and the chance of the public being incorrect for that question is greater than zero
            'publicCorrect' is set to true if the chance of the public being incorrect for that question, multiplied by
            'publicIncorrectChanceDecrease', is less than a random number between 0 and 100 (using 'randomController')
            Otherwise, 'publicCorrect' is set to false
        Otherwise
            'publicCorrect' is set to true if the chance of the public being incorrect for
            that question is less than a random number between 0 and 100 (using 'randomController')
            Otherwise, 'publicCorrect' is set to false
        'highestValueCap' is set to the highest certainty of the public for that question
        'lowestValueCap' is created
        If 'isHalfAndHalfUsed' is true
            The 'ArrayList' 'halfBarValues' is created
            'lowestValueCap' is set to the total percentage of the bars (100), divided by the number
            of bars that will be assigned random values (2), rounded to ensure it's an integer
            If 'isClueUsed' is true and 'highestValueCap' is greater than zero
                'lowestValueCap' is set to itself plus the gap between 'lowestValueCap' and 'highestValueCap'
                multiplied by 'publicCertaintyIncrease', rounded to ensure it's an integer
            'halfBarValues' is set to an 'ArrayList' containing a random number
            between 'lowestValueCap' and 'highestValueCap' (using 'randomController')
            The gap between the value in 'halfBarValues' and the total
            percentage of the bars (100) is added to 'halfBarValues'
            'potentialAnswersButtons' is iterated over
                If the current Button is enabled
                    If the current Button's text is the same as 'correctAnswer' and 'publicCorrect' is true,
                    or the current Button's text isn't the same as 'correctAnswer' and 'publicCorrect' is false
                        The first item in 'halfBarVales' is added to 'barValues'
                    Otherwise, if the current Button's text isn't the same as 'correctAnswer', and 'publicCorrect' is
                    true, or the current Button's text is the same as 'correctAnswer', and 'publicCorrect' is false
                        The second item in 'halfBarValues' is added to 'barValues'
                Otherwise, zero is added to 'barValues'
        Otherwise, four 'null' values are added to 'barVales'
            'highestValue' is created
            'lowestValueCap' is set to the total percentage of the bars (100), divided by the number
            of bars that will be assigned random values (4), rounded to ensure it's an integer
            If 'isClueUsed' is true and 'highestValueCap' is greater than zero
                'lowestValueCap' is set to itself plus the gap between 'lowestValueCap' and 'highestValueCap'
                multiplied by 'publicCertaintyIncrease', rounded to ensure it's an integer
            'highestValue' is set to a random number between
            'lowestValueCap' and 'highestValueCap' (using 'randomController')
            The 'ArrayList' 'topupValues' is created and is populated with three random numbers that
            add up to the total percentage of the bars (100) minus 'highestValue' (using 'randomController')
            A random 'null' in 'barValues' is set to 'highestValue' (using 'randomController')
            'barValues' is iterated over
                If the current item is 'null', it's set to the first integer in
                'topupValues', and the first integer in 'topupValues' is then removed
            The 'ArrayList' 'potentialAnswers' is created
            'potentialAnswersButtons' is iterated over
                The text of the current Button is added to 'potentialAnswers'
            If 'publicCorrect' is true
                The integer in 'barValues' at the same index position of 'correctAnswer'
                in 'potentialAnswers' is swapped with the largest integer in 'barValues'
            Otherwise, the 'ArrayList' 'incorrectAnswers' is created,
            and is populated with the contents of 'potentialAnswers'
                'correctAnswer' is removed from 'incorrectAnswers',
                and 'incorrectAnswer' is set to a random string in 'incorrectAnswers' (using 'randomController')
                The integer in 'barValues' at the same index position of 'incorrectAnswer'
                in 'potentialAnswers' is swapped with the largest integer in 'barValues'
        The value of each ProgressBar in the given 'ArrayList' of ProgressBars
        is set to the integer in 'barValues' in the same index position*/
    //</editor-fold>
    void populatePublicBars(
            RandomController randomController, int questionNumber, ArrayList<JTextArea> potentialAnswersText,
            ArrayList<JButton> potentialAnswersButtons, String correctAnswer, ArrayList<JProgressBar> bars,
            boolean isHalfAndHalfUsed, boolean isClueUsed)
    {
        for (int i = 0; i < 4; i++)
        {
            potentialAnswersText.get(i).setText(potentialAnswersButtons.get(i).getText());
        }

        ArrayList<Integer> barValues = new ArrayList<>();
        boolean publicCorrect;
        if (isClueUsed && publicIncorrectChances.get(questionNumber - 1) > 0)
        {
            publicCorrect = publicIncorrectChances.get(questionNumber - 1) * publicIncorrectChanceDecrease <
                    randomController.getRandomNumber(0, 100);
        }
        else
        {
            publicCorrect = publicIncorrectChances.get(questionNumber - 1) <
                    randomController.getRandomNumber(0, 100);
        }
        int highestValueCap = publicHighestCertainty.get(questionNumber - 1);
        int lowestValueCap;
        if (isHalfAndHalfUsed)
        {
            ArrayList<Integer> halfBarValues;
            lowestValueCap = Math.round(100 / 2);
            if (isClueUsed && highestValueCap > 0)
            {
                lowestValueCap = Math.round(
                        lowestValueCap + ((highestValueCap - lowestValueCap) * publicCertaintyIncrease));
            }
            halfBarValues = new ArrayList<>(Collections.singletonList(
                    randomController.getRandomNumber(lowestValueCap, highestValueCap)));
            halfBarValues.add(100 - halfBarValues.get(0));
            for (JButton button : potentialAnswersButtons)
            {
                if (button.isEnabled())
                {
                    if ((button.getText().equals(correctAnswer) && publicCorrect) ||
                            (!button.getText().equals(correctAnswer) && !publicCorrect))
                    {
                        barValues.add(halfBarValues.get(0));
                    }
                    else if ((!button.getText().equals(correctAnswer) && publicCorrect) ||
                            (button.getText().equals(correctAnswer) && !publicCorrect))
                    {
                        barValues.add(halfBarValues.get(1));
                    }
                }
                else
                {
                    barValues.add(0);
                }
            }
        }
        else
        {
            for (int i = 0; i < 4; i++)
            {
                barValues.add(null);
            }
            int highestValue;
            lowestValueCap = Math.round(100 / 4);
            if (isClueUsed && highestValueCap > 0)
            {
                lowestValueCap = Math.round(
                        lowestValueCap + ((highestValueCap - lowestValueCap) * publicCertaintyIncrease));
            }
            highestValue = randomController.getRandomNumber(lowestValueCap, highestValueCap);
            ArrayList<Integer> topupValues = randomController.getRandomNumbers(100 - highestValue, 3);
            barValues.set(randomController.getRandomNumber(0, 3), highestValue);
            for (int i = 0; i < barValues.size(); i++)
            {
                if (barValues.get(i) == null)
                {
                    barValues.set(i, topupValues.get(0));
                    topupValues.remove(0);
                }
            }

            ArrayList<String> potentialAnswers = new ArrayList<>();
            for (JButton button : potentialAnswersButtons)
            {
                potentialAnswers.add(button.getText());
            }

            if (publicCorrect)
            {
                Collections.swap(
                        barValues,
                        potentialAnswers.indexOf(correctAnswer),
                        barValues.indexOf(Collections.max(barValues)));
            }
            else
            {
                ArrayList<String> incorrectAnswers = new ArrayList<>(potentialAnswers);
                incorrectAnswers.remove(correctAnswer);
                String incorrectAnswer = incorrectAnswers.get(randomController.getRandomNumber(0, incorrectAnswers.size() - 1));
                Collections.swap(
                        barValues,
                        potentialAnswers.indexOf(incorrectAnswer),
                        barValues.indexOf(Collections.max(barValues)));
            }
        }

        for (int i = 0; i < 4; i++)
        {
            bars.get(i).setValue(barValues.get(i));
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Populates the 'enabled' values of given help facility buttons using a given PlayersController and a given index...">
    //...position of the current player
        /*If the 'askpublic' help facility has been used by the current player at the given index position
        (using the given PlayersController), 'enabled' of the given 'askThePublicButton' is set to false
        If the 'halfandhalf' help facility has been used by the current player at the given index position
        (using the given PlayersController), 'enabled' of the given 'halfAndHalfButton' is set to false
        If the 'clue' help facility has been used by the current player at the given index position
        (using the given PlayersController), 'enabled' of the given 'giveUsAClueButton' is set to false*/
    //</editor-fold>
    void populateHelpFacilities(
            PlayersController playersController, int currentPlayerIndex,
            JButton askThePublicButton, JButton halfAndHalfButton, JButton giveUsAClueButton)
    {
        if (playersController.getPlayerHelpFacilityUsed(currentPlayerIndex, "askpublic"))
        {
            askThePublicButton.setEnabled(false);
        }
        if (playersController.getPlayerHelpFacilityUsed(currentPlayerIndex, "halfandhalf"))
        {
            halfAndHalfButton.setEnabled(false);
        }
        if (playersController.getPlayerHelpFacilityUsed(currentPlayerIndex, "clue"))
        {
            giveUsAClueButton.setEnabled(false);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Populates the text of a given 'winnersLabel' and 'leaderboardTextArea' using a given PlayersController">
        /*'playersSize' is set to the size of the players list model (using the given PlayersController)
        If 'playersSize' is one
            The 'Player' 'player' is set to the first (and only) 'Player'
            in the players list model (using the given PlayersController)
            'playerName' is set to the name of 'player', and 'playerMoney' is set to the money of 'player'
            The text of the given 'winnersLabel' is set according to the value of 'playerMoney'
        Otherwise
            The 'DefaultListModel' 'orderedPlayers' is created, and is populated with the players
            sorted in order of how much money they have (descending order) (using the given PlayersController)
            If the money of the first 'Player' in 'orderedPlayers' is zero
                'winnersLabel' is set to "Nobody won anything!", and 'leaderboardScrollPane'
                (containing the given 'leaderboardTextArea') is removed from the given Panel
            If the money of the first two 'Player's in 'orderedPlayers' is the same
                All 'Player's with the same money as the first 'Player' in 'orderedPlayers' are added to 'winnersLabel'
                Players with less money than the first 'Player' in 'orderedPlayers' are added to 'leaderboardTextArea'
            Otherwise
                The first 'Player' in 'orderedPlayers' is added to 'winnersLabel',
                and the rest are added to 'leaderboardTextArea'
        If 'leaderboardTextArea' is empty, 'leaderboardScrollPane'
        (containing 'leaderboardTextArea') is removed from the given Panel
        Otherwise, if the last character in 'leaderboardTextArea' is a newline, the newline is removed*/
    //</editor-fold>
    void populateLeaderboard(
            PlayersController playersController, JPanel panel,
            JLabel winnersLabel, JScrollPane leaderboardScrollPane, JTextArea leaderboardTextArea)
    {
        int playersSize = playersController.getPlayersSize();

        if (playersSize == 1)
        {
            Player player = playersController.getPlayers().get(0);
            String playerName = player.getName();
            int playerMoney = player.getMoney();
            if (playerMoney == 0)
            {
                winnersLabel.setText("Commiserations '" + playerName + "'. You won nothing.");
            }
            else if (playerMoney <= 1000)
            {
                winnersLabel.setText("Fair attempt, '" + playerName + "'. You won £" + playerMoney);
            }
            else if (playerMoney <= 32000)
            {
                winnersLabel.setText("Well played, '" + playerName + "'. You won £" + playerMoney);
            }
            else if (playerMoney <= 500000)
            {
                winnersLabel.setText("Well done, '" + playerName + "'! You won £" + playerMoney);
            }
            else if (playerMoney == 1000000)
            {
                winnersLabel.setText(
                        "Congratulations, '" + playerName + "'! You won the grand prize of £" + playerMoney);
            }
        }
        else
        {
            DefaultListModel<Player> orderedPlayers = playersController.getPlayersOrdered();
            if (orderedPlayers.get(0).getMoney() == 0)
            {
                winnersLabel.setText("Nobody won anything!");
            }
            else if (orderedPlayers.get(0).getMoney() == orderedPlayers.get(1).getMoney())
            {
                winnersLabel.setText("Winners (each winning £" + orderedPlayers.get(0).getMoney() + "): '" +
                        orderedPlayers.get(0).getName() + "'");
                for (int i = 1; i < playersSize; i++)
                {
                    if (orderedPlayers.get(i).getMoney() == orderedPlayers.get(0).getMoney())
                    {
                        winnersLabel.setText(winnersLabel.getText() + ", '" + orderedPlayers.get(i).getName() + "'");
                    }
                    else
                    {
                        leaderboardTextArea.append("'" + orderedPlayers.get(i).getName() + "': £" +
                                orderedPlayers.get(i).getMoney() + "\n");
                    }
                }
            }
            else
            {
                winnersLabel.setText("Winner: '" + orderedPlayers.get(0).getName() + "', with £" +
                        orderedPlayers.get(0).getMoney());
                for (int i = 1; i < playersSize; i++)
                {
                    leaderboardTextArea.append("'" + orderedPlayers.get(i).getName() + "': £" +
                            orderedPlayers.get(i).getMoney() + "\n");
                }
            }
        }

        String leaderboardText = leaderboardTextArea.getText();
        if (leaderboardText.equals(""))
        {
            panel.remove(leaderboardScrollPane);
        }
        else if (leaderboardText.substring(leaderboardText.length() - 1).equals("\n"))
        {
            leaderboardTextArea.setText(leaderboardText.substring(0, leaderboardText.length() - 1));
        }
    }


    //Getters and setters:

    JFrame getGameFrame()
    {
        return gameFrame;
    }

    String getImagesPath()
    {
        return imagesPath;
    }
}