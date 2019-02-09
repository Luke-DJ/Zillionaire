import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

//Created by Luke Dicken - Jones
class QuestionWriter
{
    //Declaring the variable for the path to the 'questions' folder
    private String questionsPath;


    //Constructor - instantiating the class's instance variables
    QuestionWriter(String questionsPath)
    {
        this.questionsPath = questionsPath;
    }


    //<editor-fold defaultstate="collapsed" desc="Attempts to add a given question to the corresponding file of a given category and question number">
        /*The 'BufferedWriter' 'writer' is created using 'questionsPath', and 'StandardOpenOption.CREATE'
        and 'StandardOpenOption.APPEND' are used so that the program will create the
        file if it doesn't exist, and then appends the new content
        'questionAndAnswers' is iterated over, each item is written to the file using 'writer' (preceded by a new line),
        and 'writer' is 'flushed' (clearing the current contents of the stream)
        A final new line is written to the file, and 'writer' is 'closed' (flushing and then closing the stream)
        If this is unsuccessful, an error popup is displayed
    Appropriate sounds are played according to whether or not the new question is added successfully*/
    //</editor-fold>
    void Write(SoundController soundController, String category, int questionNumber, ArrayList<String> questionAndAnswers)
    {
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(questionsPath + category.toLowerCase() + "/" + questionNumber),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            for (String item : questionAndAnswers)
            {
                writer.write("\n" + item);
                writer.flush();
            }
            writer.write("\n");
            writer.close();
        }
        catch (Exception e)
        {
            soundController.PlaySound("incorrect", false, false);
            JOptionPane.showMessageDialog(
                    new JPanel(),
                    "Error - Could not write the new question to file",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}