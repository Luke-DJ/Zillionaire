import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

//Created by Luke Dicken - Jones
class RandomController
{
    //Declaring the instance variable for the random generator
    private SecureRandom randomGenerator;


    //Constructor - instantiating the class's instance variables
    RandomController()
    {
        this.randomGenerator = new SecureRandom();
    }


    //<editor-fold defaultstate="collapsed" desc="Returns a random number between two given numbers (lowest and highest)">
		/*A value between zero and the gap between the given lowest and highest numbers (+1)
		is drawn from 'randomGenerator''s sequence, and is added to the given lowest number*/
    //</editor-fold>
    int getRandomNumber(int lowestNumber, int highestNumber)
    {
        return (randomGenerator.nextInt((highestNumber - lowestNumber) + 1) + lowestNumber);
    }

    //<editor-fold defaultstate="collapsed" desc="Returns an 'ArrayList' of a given amount of random numbers that add up to a given 'highest' number">
        /*If the requested amount of random numbers is one, then an 'ArrayList' of the given 'highest' number is returned
        Otherwise, the 'ArrayList' 'randomPoints' is created, and 'numberOfNumbers' is iterated over,
        with a random number between zero and the given 'highest' number being added to 'randomPoints' each iteration
        'randomPoints' is then sorted into ascending order
        The 'ArrayList' 'pointGaps' is created, and 'numberOfNumbers' is iterated over
            the first (and smallest) number in 'randomPoints' is first to be added to 'pointGaps',
            followed by the 'gap' between each iteration's previous number
            in 'randomPoints' and that iteration's current number in 'randomPoints'
            finally the 'gap' between the given 'highest' number and that
            iteration's previous number in 'randomPoints' is added to 'pointGaps'
        'pointGaps' is then returned*/
    //</editor-fold>
    ArrayList<Integer> getRandomNumbers(int highestNumber, int numberOfNumbers)
    {
        if (numberOfNumbers == 1)
        {
            return new ArrayList<>(Collections.singletonList(highestNumber));
        }

        ArrayList<Integer> randomPoints = new ArrayList<>();
        for (int i = 0; i < numberOfNumbers - 1; i++)
        {
            randomPoints.add(getRandomNumber(0, highestNumber));
        }
        Collections.sort(randomPoints);

        ArrayList<Integer> pointGaps = new ArrayList<>();
        for (int i = 0; i < numberOfNumbers; i++)
        {
            if (i == 0)
            {
                pointGaps.add(randomPoints.get(i));
            }
            else if (i == numberOfNumbers - 1)
            {
                pointGaps.add(highestNumber - randomPoints.get(i - 1));
            }
            else
            {
                pointGaps.add(randomPoints.get(i) - randomPoints.get(i - 1));
            }
        }

        return pointGaps;
    }

    //<editor-fold defaultstate="collapsed" desc="Randomises the positions of the items in a given 'ArrayList'">
        /*The given 'ArrayList' is iterated over,
        and each item is swapped with another item at a randomly selected position in the 'ArrayList'*/
    //</editor-fold>
    void shuffleArrayList(ArrayList<String> arrayList)
    {
        for (int i = 0; i < arrayList.size(); i++)
        {
            Collections.swap(arrayList, i, getRandomNumber(0, arrayList.size() - 1));
        }
    }
}