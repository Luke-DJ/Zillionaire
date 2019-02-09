//Created by Luke Dicken - Jones
public class Player implements Comparable<Player>
{
    //Declaring instance variables for the player's name/money
    private String name;
    private int money;
    //Declaring instance variables for the player's help facility usage
    private boolean askPublicUsed;
    private boolean halfAndHalfUsed;
    private boolean clueUsed;


    //Constructor - instantiating the class's instance variables
    public Player(String name)
    {
        this.name = name;
        this.money = 0;

        askPublicUsed = false;
        halfAndHalfUsed = false;
        clueUsed = false;
    }


    /*Overriding the class's 'natural comparison method' so that a collection of
    players can be sorted in order of how much money they have (descending order)*/
    @Override
    public int compareTo(Player player)
    {
        return Integer.compare(player.getMoney(), this.money);
    }


    //Getters and setters

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    int getMoney()
    {
        return money;
    }

    void setMoney(int money)
    {
        this.money = money;
    }

    boolean isAskPublicUsed()
    {
        return askPublicUsed;
    }

    void setAskPublicUsed(boolean askPublicUsed)
    {
        this.askPublicUsed = askPublicUsed;
    }

    boolean isHalfAndHalfUsed()
    {
        return halfAndHalfUsed;
    }

    void setHalfAndHalfUsed(boolean halfAndHalfUsed)
    {
        this.halfAndHalfUsed = halfAndHalfUsed;
    }

    boolean isClueUsed()
    {
        return clueUsed;
    }

    void setClueUsed(boolean clueUsed)
    {
        this.clueUsed = clueUsed;
    }

    public String toString()
    {
        return this.name;
    }
}