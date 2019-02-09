import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Created by Luke Dicken - Jones
class QuestionsController
{
    //Declaring instance variables for the required controllers
    private RandomController randomController;
    private SoundController soundController;
    //Declaring and instantiating the variable for the path to the 'questions' folder
    private String questionsPath = "resources/questions/";
    //Declaring and instantiating the variable for the 'ArrayList' of categories
    private ArrayList<String> categories = new ArrayList<>(Arrays.asList("general", "sport", "countries"));
    //Declaring instance variables for the question reader and question writer
    private QuestionReader questionReader;
    private QuestionWriter questionWriter;
    //Declaring the instance variable for the 'ArrayList' of used questions
    private ArrayList<ArrayList<String>> usedQuestions;
    //Declaring and instantiating the variable for the maximum number of characters an entry field can consist of
    private int entryFieldMaxCharacters = 100;
    //Declaring and instantiating the variable for the regex of acceptable characters an entry field can consist of
    private String entryFieldCharactersRegex = "[a-zA-Z0-9+=().,'?! -]+";
    //Declaring and instantiating the 'ArrayList' that contains fifteen integers representing each question's value
    private ArrayList<Integer> questionValues = new ArrayList<>(Arrays.asList(
            100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000));


    //Constructor - instantiating the class's instance variables
    QuestionsController(SoundController soundController, RandomController randomController)
    {
        this.soundController = soundController;
        this.randomController = randomController;

        this.questionReader = new QuestionReader(questionsPath, categories);
        this.questionWriter = new QuestionWriter(questionsPath);
        this.usedQuestions = new ArrayList<>();
    }


    //<editor-fold defaultstate="collapsed" desc="Returns an 'ArrayList' containing the six strings that make up a question (question, correct answer, three...">
    //...incorrect answers, hint), corresponding with a given category and question number
		/*The 'ArrayList's 'questions' and 'unusedQuestions' are created,
		with 'questions' being populated by 'questionReader' using the given category and question number
		'questions' is iterated over,
		and any questions that aren't found in 'usedQuestions' are added to 'unusedQuestions'
		The 'ArrayList' 'randomQuestion' is created
		    If there are no unused questions ('unusedQuestions' is empty),
		    then 'randomQuestion' becomes a randomly selected question from 'questions'
		    Otherwise, 'randomQuestion' becomes a randomly selected question from 'unusedQuestions',
		    and is added to 'usedQuestions'
		'randomQuestion' is then returned*/
    //</editor-fold>
    ArrayList<String> getQuestion(String category, int questionNumber)
    {
        ArrayList<ArrayList<String>> questions = questionReader.Read(category, questionNumber);
        ArrayList<ArrayList<String>> unusedQuestions = new ArrayList<>();
        for (ArrayList<String> question : questions)
        {
            if (!usedQuestions.contains(question))
            {
                unusedQuestions.add(question);
            }
        }
        ArrayList<String> randomQuestion;
        if (unusedQuestions.isEmpty())
        {
            randomQuestion = questions.get(randomController.getRandomNumber(
                    0,
                    questions.size() - 1));
        }
        else
        {
            randomQuestion = unusedQuestions.get(randomController.getRandomNumber(
                    0,
                    unusedQuestions.size() - 1));
            usedQuestions.add(randomQuestion);
        }
        return randomQuestion;
    }

    //<editor-fold defaultstate="collapsed" desc="Attempts to add a given question to the corresponding file of a given category and question number">
        /*The 'ArrayList' 'questionAndAnswers' is created and contains
        the given question, correct answer, incorrect answers, and hint
		'questionAndAnswers' is first iterated over to strip leading/trailing
		whitespace and replace instances of multiple spaces with a single space
		'questionAndAnswers' is then iterated over a second time
			If any item in 'questionAndAnswers' fails to pass some specific checks, an error popup is displayed
			Otherwise, the given category string is formatted
				If the question doesn't already exist in the file corresponding with the given category (formatted)
				and question number, then 'questionAndAnswers' is added to the file and a success popup is displayed
				Otherwise, an error popup is displayed
	Appropriate sounds are played according to whether or not the new question is added successfully*/
    //</editor-fold>
    void addQuestion(
            String category, int questionNumber, String question, String correctAnswer,
            String incorrectAnswer1, String incorrectAnswer2, String incorrectAnswer3, String hint)
    {
        ArrayList<String> questionAndAnswers = new ArrayList<>(Arrays.asList(
                question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, hint));

        for (int i = 0; i < questionAndAnswers.size(); i++)
        {
            questionAndAnswers.set(i, questionAndAnswers.get(i).trim().replaceAll(" +", " "));
        }

        for (int i = 0; i < questionAndAnswers.size(); i++)
        {
            if (questionAndAnswers.get(i) == null ||
                    questionAndAnswers.get(i).replaceAll(" ", "").isEmpty())
            {
                soundController.PlaySound("incorrect", false, false);
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Incorrect/incomplete question information",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                break;
            }
            else if (questionAndAnswers.get(i).length() > entryFieldMaxCharacters)
            {
                soundController.PlaySound("incorrect", false, false);
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Entered text exceeds the " + entryFieldMaxCharacters + " character limit",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                break;
            }
            else if (!questionAndAnswers.get(i).matches(entryFieldCharactersRegex))
            {
                soundController.PlaySound("incorrect", false, false);
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Illegal characters in entered text",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                break;
            }
            else if (Collections.frequency(questionAndAnswers, questionAndAnswers.get(i)) > 1)
            {
                soundController.PlaySound("incorrect", false, false);
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Duplicates in entered text",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                break;
            }
            else if (i == questionAndAnswers.size() - 1)
            {
                if (category.equals("General Knowledge"))
                {
                    category = "general";
                }
                else
                {
                    category = category.toLowerCase();
                }

                if (!questionReader.Read(category, questionNumber).contains(questionAndAnswers))
                {
                    questionWriter.Write(soundController, category, questionNumber, questionAndAnswers);
                    soundController.PlaySound("correct", false, false);
                    JOptionPane.showMessageDialog(
                            new JPanel(),
                            "Question added successfully",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    soundController.PlaySound("incorrect", false, false);
                    JOptionPane.showMessageDialog(
                            new JPanel(),
                            "Error - Question already exists",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Returns the monetary value of a given question number">
		/*'questionValues' is iterated over (starting at 1)
			If the given question number is equal to the number of the current iteration,
			the integer at that position (-1) in 'questionValues' is returned
			Otherwise, zero is returned, as the value of a question number above/below 1-15 is unknown*/
    //</editor-fold>
    int questionNumberToMoney(int questionNumber)
    {
        for (int i = 1; i <= questionValues.size(); i++)
        {
            if (questionNumber == i)
            {
                return questionValues.get(i - 1);
            }
        }
        return 0;
    }

    //Clears all used questions ('usedQuestions')
    void clearUsedQuestions()
    {
        this.usedQuestions.clear();
    }
}