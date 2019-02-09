import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

//Created by Luke Dicken - Jones
public class ClueGUI
{
    //Declaring the variables for the swing components
    private JPanel cluePanel;
    private JLabel titleLabel;
    private JLabel clueQuestionLabel;


    /*Constructor - instantiating the class's instance variables
    and managing the look/functionality of the swing components*/
    ClueGUI(GameController gameController, String clue)
    {
        UIController uiController = gameController.getUiController();
        String imagesPath = uiController.getImagesPath();

        //Setting up the "Give us a Clue" JFrame
        JFrame frame = new JFrame("Give us a Clue");
        frame.setContentPane(this.cluePanel);
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

        //Setting the text of 'clueQuestionLabel' to 'clue'
        clueQuestionLabel.setText(clue);

        //Ensuring 'clueQuestionLabel's text fits within the component
        uiController.shrinkFontToComponent(clueQuestionLabel, clueQuestionLabel.getText());

        //Changes the foreground and background colours and font of the components in the panel to those of 'gameFrame'
        uiController.refreshColours(cluePanel);
        uiController.refreshFonts(cluePanel);
    }
}