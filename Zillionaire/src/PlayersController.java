import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Created by Luke Dicken - Jones
class PlayersController
{
    //Declaring instance variables for the required controllers
    private SoundController soundController;
    //Declaring the instance variable for the list model of players
    private DefaultListModel<Player> players;
    //Declaring and instantiating the variable for the maximum number of characters a player's name can consist of
    private int playerNameMaxCharacters = 26;
    //Declaring and instantiating the variable for the regex of acceptable characters a player's name can consist of
    private String playerNameCharactersRegex = "[a-zA-Z0-9., -]+";


    //Constructor - instantiating the class's instance variables
    PlayersController(SoundController soundController)
    {
        this.soundController = soundController;

        this.players = new DefaultListModel<>();
    }


    //Getter for the players list model
    DefaultListModel<Player> getPlayers()
    {
        return players;
    }

    //<editor-fold defaultstate="collapsed" desc="Returns a modified version of the players list model that's sorted by much money the players have (descending order)">
        /*Creates a new 'ArrayList' containing the elements from the players list model
        Sorts the 'ArrayList'
        Adds the sorted elements of the 'ArrayList' to a new 'DefaultListModel'
        Returns the new 'DefaultListModel'*/
    //</editor-fold>
    DefaultListModel<Player> getPlayersOrdered()
    {
        ArrayList<Player> playersList = Collections.list(players.elements());
        Collections.sort(playersList);
        DefaultListModel<Player> playersModel = new DefaultListModel<>();
        for (Player player : playersList)
        {
            playersModel.addElement(player);
        }
        return (playersModel);
    }

    //<editor-fold defaultstate="collapsed" desc="Swaps the positions of two players at given index positions in the players list model">
        /*Creates a new 'ArrayList' containing the elements from the players list model
        Swaps the players at the given index positions in the new 'ArrayList'
        Clears the contents of the players list model
        Adds the contents of the 'ArrayList' back into the players list model*/
    //</editor-fold>
    private void swapPlayers(int firstPlayerIndex, int secondPlayerIndex)
    {
        ArrayList<Player> playersList = Collections.list(players.elements());
        Collections.swap(playersList, firstPlayerIndex, secondPlayerIndex);
        players.clear();
        for (Player player : playersList)
        {
            players.addElement(player);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Moves the currently selected player in the list of players up one position">
        /*If a player is selected, if there's more than one player,
        and if the selected player isn't already at the top of the list of players
            Swap the selected player with the player at the index position one below the selected player
            Set the selection of the list of players to the index position one below the current selection
    Appropriate sounds are played according to whether or not the player was moved up in the list successfully*/
    //</editor-fold>
    void movePlayerUp(JList playersList)
    {
        if (!playersList.isSelectionEmpty() && players.size() > 1)
        {
            int playerIndex = players.indexOf(playersList.getSelectedValue());
            if (playerIndex > 0)
            {
                swapPlayers(playerIndex, playerIndex - 1);
                playersList.setSelectedIndex(playerIndex - 1);
                soundController.PlaySound("correct", false, false);
                return;
            }
        }
        soundController.PlaySound("incorrect", false, false);
    }

    //<editor-fold defaultstate="collapsed" desc="Moves the currently selected player in the list of players down one position">
        /*If a player is selected, if there's more than one player,
        and if the selected player isn't already at the bottom of the list of players
            Swap the selected player with the player at the index position one above the selected player
            Set the selection of the list of players to the index position one above the current selection
    Appropriate sounds are played according to whether or not the player was moved down in the list successfully*/
    //</editor-fold>
    void movePlayerDown(JList playersList)
    {
        if (!playersList.isSelectionEmpty() && players.size() > 1)
        {
            int playerIndex = players.indexOf(playersList.getSelectedValue());
            if (playerIndex < players.size() - 1)
            {
                swapPlayers(playerIndex, playerIndex + 1);
                playersList.setSelectedIndex(playerIndex + 1);
                soundController.PlaySound("correct", false, false);
                return;
            }
        }
        soundController.PlaySound("incorrect", false, false);
    }

    //Returns the size of the players list model
    int getPlayersSize()
    {
        return players.size();
    }

    //<editor-fold defaultstate="collapsed" desc="Attempts to add a player to the players list model, and returns true if the new player is added successfully">
        /*Strips leading/trailing whitespace from the given name and
        replaces instances of multiple spaces with a single space
        If there aren't any players, a new player is added with the given name, and the method returns true
        Otherwise, if the given name passes some specific checks,
            existing players with the same name are searched for
            if a player is found with the same name, an error popup is displayed, and the method returns false
            otherwise, a new player is added with the given name, and the method returns true
        If the given name doesn't pass these specific checks, an error popup is displayed, and the method returns false
    Appropriate sounds are played according to whether or not the new player is added successfully*/
    //</editor-fold>
    boolean addPlayer(String playerName)
    {
        playerName = playerName.trim().replaceAll(" +", " ");
        if (getPlayersSize() == 0)
        {
            players.addElement(new Player(playerName));
            return true;
        }
        else if (!playerName.equals("") &&
                playerName.length() <= playerNameMaxCharacters &&
                playerName.matches(playerNameCharactersRegex))
        {
            for (int i = 0; i < getPlayersSize(); i++)
            {
                if (getPlayerName(i).equals(playerName))
                {
                    soundController.PlaySound("incorrect", false, false);
                    JOptionPane.showMessageDialog(
                            new JPanel(),
                            "Error - A player with that name already exists",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    break;
                }
                else if (getPlayersSize() == i + 1)
                {
                    players.addElement(new Player(playerName));
                    soundController.PlaySound("correct", false, false);
                    return true;
                }
            }
        }
        else
        {
            soundController.PlaySound("incorrect", false, false);
            if (playerName.equals(""))
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Name cannot be blank/consist of only spaces",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if (playerName.length() > playerNameMaxCharacters)
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Name exceeds the " + playerNameMaxCharacters + " character limit",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if (!playerName.matches(playerNameCharactersRegex))
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Illegal characters in name",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Attempts to remove a player from the players list model, and returns true if the player is removed successfully">
        /*If there aren't any players (which shouldn't ever happen,
        but this code exists just in case), an error popup is displayed, and the method returns false
        If there's only one player, an error popup is displayed
        (as the game should never have no players), and the method returns false
        Otherwise, players with the given name are searched for
            if a player is found with the given name, they are removed
            from the players list model, and the method returns true
            otherwise, an error popup is displayed, and the method returns false
    Appropriate sounds are played according to whether or not the player is removed successfully*/
    //</editor-fold>
    boolean removePlayer(String playerName)
    {
        if (getPlayersSize() <= 1)
        {
            soundController.PlaySound("incorrect", false, false);
            if (getPlayersSize() == 0)
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - There are no players to remove",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
            else if (getPlayersSize() == 1)
            {
                JOptionPane.showMessageDialog(
                        new JPanel(),
                        "Error - Cannot remove the only player",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        else
        {
            for (int i = 0; i < getPlayersSize(); i++)
            {
                if (getPlayerName(i).equals(playerName))
                {
                    players.removeElementAt(i);
                    soundController.PlaySound("correct", false, false);
                    return true;
                }
                else if (i == getPlayersSize() - 1)
                {
                    soundController.PlaySound("incorrect", false, false);
                    JOptionPane.showMessageDialog(
                            new JPanel(),
                            "Error - Cannot find a player with the name '" + playerName + "' to remove",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return false;
    }

    //Returns the name of a player in the players list model at a given index position
    String getPlayerName(int playersIndex)
    {
        return players.get(playersIndex).getName();
    }

    //Returns the amount of money a player in the players list model at a given index position has
    int getPlayerMoney(int playersIndex)
    {
        return players.get(playersIndex).getMoney();
    }

    /*Adds a given amount of money to a player in the players list model at a given index position
		(sets the amount of money the player has to their current amount of money plus the given amount of money)*/
    void addPlayerMoney(int playersIndex, int moneyAmount)
    {
        players.get(playersIndex).setMoney(players.get(playersIndex).getMoney() + moneyAmount);
    }

    //Resets the amount of money a player in the players list model at a given index position has (to zero)
    private void resetPlayerMoney(int playersIndex)
    {
        players.get(playersIndex).setMoney(0);
    }

    //Resets the amount of money every player in the players list model has (to zero)
    void resetAllPlayerMoney()
    {
        for (int i = 0; i < getPlayersSize(); i++)
        {
            resetPlayerMoney(i);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Returns true if the help facility corresponding with a given name has been used by a player in the players list...">
    //...model at a given index position
		/*If the given string is an actual help facility's name, then the result of
		the player's 'is used' method that corresponds with the given string is returned
		Otherwise, true is returned so that no action is taken based on an incorrect
		understanding that something other than the actual help facilities hasn't been used yet*/
    //</editor-fold>
    boolean getPlayerHelpFacilityUsed(int playersIndex, String helpFacilityName)
    {
        switch (helpFacilityName.toLowerCase())
        {
            case "askpublic":
                return players.get(playersIndex).isAskPublicUsed();
            case "halfandhalf":
                return players.get(playersIndex).isHalfAndHalfUsed();
            case "clue":
                return players.get(playersIndex).isClueUsed();
            default:
                return true;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Sets the usage of a help facility corresponding with a given name of a player in the players list model at a...">
    //...given index position to a given boolean value
		/*If the given string is an actual help facility's name, then the player's 'set used' method that corresponds with
		the given string is used to set the player's usage of that help facility to the given boolean value*/
    //</editor-fold>
    void setPlayerHelpFacilityUsed(int playersIndex, String helpFacilityName, boolean bool)
    {
        switch (helpFacilityName.toLowerCase())
        {
            case "askpublic":
                players.get(playersIndex).setAskPublicUsed(bool);
                break;
            case "halfandhalf":
                players.get(playersIndex).setHalfAndHalfUsed(bool);
                break;
            case "clue":
                players.get(playersIndex).setClueUsed(bool);
                break;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Resets the usage of all help facilities of a player in the players list model at a given index position (to false)">
		/*An 'ArrayList' of strings containing the names of the help facilities is iterated over
			The usage of each help facility is retrieved,
			and if the usage is set to true, the usage is then 'reset' (set to false)*/
    //</editor-fold>
    private void resetPlayerHelpFacilityUsed(int playersIndex)
    {
        for (String helpFacilityName : new ArrayList<>(Arrays.asList("askpublic", "halfandhalf", "clue")))
        {
            if (getPlayerHelpFacilityUsed(playersIndex, helpFacilityName))
            {
                setPlayerHelpFacilityUsed(playersIndex, helpFacilityName, false);
            }
        }
    }

    //Resets the usage of all help facilities of every player in the players list model (to false)
    void resetAllPlayerHelpFacilityUsed()
    {
        for (int i = 0; i < getPlayersSize(); i++)
        {
            resetPlayerHelpFacilityUsed(i);
        }
    }
}