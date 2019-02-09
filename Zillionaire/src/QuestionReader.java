import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Created by Luke Dicken - Jones
class QuestionReader
{
    //Declaring the variable for the path to the 'questions' folder
    private String questionsPath;
    //Declaring the variable for the 'ArrayList' of categories
    private ArrayList<String> categories;


    //Constructor - instantiating the class's instance variables
    QuestionReader(String questionsPath, ArrayList<String> categories)
    {
        this.questionsPath = questionsPath;

        this.categories = categories;
    }


    //<editor-fold defaultstate="collapsed" desc="Returns an 'ArrayList' of 'ArrayList's that contain the six strings that make up a question (question, correct...">
    /*...answer, three incorrect answers, hint), taken from a file
    that corresponds with a given category and question number*/
        /*The given category is formatted, and the string 'path' is set to 'questionsPath'
        If the given question number is between one and fifteen (the first and last questions)
            An 'ArrayList' of strings containing the names of the categories is iterated over
                If the given category (formatted) is an actual category's name, then the given category
                name (formatted) and question number are added to 'path', separated by a forward slash
        The 'ArrayList's 'sortedQuestions' and 'rawQuestions' are created,
        with 'rawQuestions' being populated with the lines contained in the file at 'path'
        After every seven lines in 'rawQuestions', the following six lines of 'rawQuestions' are individually
        added to a new 'ArrayList' called 'currentQuestion', which is then added to 'sortedQuestions'
        'sortedQuestions' is returned once this process is completed
    A placeholder set of questions, answers, and hints is returned if the given category
    and question number don't correspond with any of the files in the 'questions' folder*/
    //</editor-fold>
    ArrayList<ArrayList<String>> Read(String category, int questionNumber)
    {
        category = category.toLowerCase();
        String path = questionsPath;

        if (questionNumber >= 1 && questionNumber <= 15)
        {
            for (String actualCategory : categories)
            {
                if (category.equals(actualCategory))
                {
                    path += category + "/" + questionNumber;
                    break;
                }
            }
        }

        try
        {
            ArrayList<ArrayList<String>> sortedQuestions = new ArrayList<>();
            ArrayList<String> rawQuestions = new ArrayList<>(Files.readAllLines(Paths.get(path)));
            for (int i = 0; i < rawQuestions.size(); i += 7)
            {
                ArrayList<String> currentQuestion = new ArrayList<>();
                for (int j = i; j < i + 6; j++)
                {
                    currentQuestion.add(rawQuestions.get(j));
                }
                sortedQuestions.add(currentQuestion);
            }
            return sortedQuestions;
        }
        catch (Exception ex)
        {
            return new ArrayList<>(Collections.singletonList(new ArrayList<>(Arrays.asList(
                    "Question", "Answer", "Incorrect answer 1", "Incorrect answer 2", "Incorrect answer 3", "Hint"
            ))));
        }
    }
}