import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty; 
/**
* This class will contain the rules of the game, instantiate the players and map, and deal with how the 
* graphics should be set up. 
* @author R.Wei, A.Wang
* @version 060718 
**/
public class Game
{
  //instantiate the variables needed for the game
  private BooleanProperty endGame;
  private boolean endMatch; 
  private BooleanProperty drawPhase;
  private IntegerProperty currentTurn;
  private int turnCount; 
  private int maxTurns; 
  private int numWins;
  private int totalGames; 
  private Player player1, player2;
  private String winnerId;
  /**
   * Constructor - creates a Game object with the given players and the given conditions
   * 
   * @param playerOne Player object for player1
   * @param playerTwo Player object for player2
   * @param wins number of wins needed for a player to win the match
   * @param games total number of games in the match
   */
  public Game(Player playerOne, Player playerTwo, int wins, int games)
  {
      numWins = wins;
      totalGames = games;
      player1 = playerOne;
      player2 = playerTwo;
      drawPhase = new SimpleBooleanProperty();
      currentTurn = new SimpleIntegerProperty(1);
      endGame = new SimpleBooleanProperty();
      turnCount = 1; 
      maxTurns = 1;
  }
  /**
   * method refreshGameState - method refreshes the status of both player objects
   */
  public void refreshGameState()
  {
      player1.setHealth(3);
      player1.setWalking(false);
      player2.setHealth(3);
      player2.setWalking(false); 
      endGame.set(false); 
  }
  /**
   * Returns the number of wins in the current match. 
   * @return the number of wins in the current match
   */
  public int getWins()
  {
      return numWins;
  }
  
  /**
   * Returns the total number of games in the current match. 
   * @return the number of games in the current match
   */
  public int getGames()
  {
      return totalGames;
  }
  
  /**
   * Returns player one in the match.
   * @return player one
   */
  public Player getPlayer1()
  {
      return player1;
  }
  
  /**
   * Returns player two in the match.
   * @return player two
   */
  public Player getPlayer2()
  {
      return player2;
  }
  /**
  * Checks to see if the game is over.
  * @return If game is over (true if so, false otherwise)
  **/
  public boolean gameState()
  {
        return endGame.get();
  }
  /**
   * method getEndGameProperty - method returns the BooleanProperty object controlling
   * when the combat phase ends
   * 
   * @return the BooleanProperty object controlling when the combat phase ends
   */
  public BooleanProperty getEndGameProperty()
  {
      return endGame; 
  }
  /**
   * method setEndGameProperty - method sets the new state of the BooleanProperty object
   * controlling when the combat phase ends
   * 
   * @param newEndGame the new state of this object
   */
  public void setEndGameProperty(boolean newEndGame)
  {
      endGame.set(newEndGame); 
  }
  /**
  * Checks to see if the match is over.
  * @return If match is over (true if so, false otherwise)
  **/
  public boolean matchState()
  {
    if(gameState())
    {
        if (player1.getWins() >= numWins || player2.getWins() >= numWins)
        {
            endMatch = true;
        }
    }
    return endMatch; 
  }
  /**
  * Checks if one player is dead. Adds a win to the players who won, if necessary. 
  **/
  public void checkWinCondition()
  {
    if (player1.isDead() || player2.isDead()) //if either player is dead
    {  
      endGame.set(true); //end the game
    }
  }
  
  /**
  * Returns the winner of the game.
  * @return The winner of the game
  **/
  public int getGameWinner()
  {
    if (player1.isDead())
      return 2;
    else 
    {  
      if (player2.isDead())
        return 1;
    }
    return 0;
  }
  /**
   * Returns the winner of the match.
   * @return The winner of the match
   */
  public Player getMatchWinner()
  {
      if (matchState() && (player1.isDead() || player2.isDead()))
      {
          if (player1.getWins() >= player2.getWins())
          {
              setWinnerId("player1");
              return player1;
          }
          else
          {
              setWinnerId("player2");
              return player2;
          }
      }
      return null;
  }
  /**
   * sets ID of the winner (1 or 2)
   * @param Player Object of Winner
   */
  public void setWinnerId(String player)
  {
      winnerId = player.substring(6);
  }
  /**
   * returns the ID of the winner
   * @return String of ID of winner
   */
  public String getWinnerId()
  {
      return winnerId;
  }
  /**
   * change the state of the drawPhase (change it from true to false or vice versa)
   * @param drawPhase true or false
   */
  public void setDrawPhase(boolean value)
  {
      drawPhase.set(value); 
  }
  /**
   * return BooleanProperty representing active draw phase or not
   * @return BooleanProperty
   */
  public BooleanProperty getDrawProperty()
  {
      return drawPhase;
  }
  /**
   * return Boolean value in drawPhase
   * @return Boolean active draw phase or not
   */
  public boolean getDrawPhase()
  {
      return drawPhase.get(); 
  }
  /**
   * count lines drawn during drawphase
   */
  public void incrementLine()
  {
      turnCount++; 
      if(turnCount >= maxTurns)
          //end drawPhase
          setDrawPhase(false);
  }
  /**
   * get if it's player1 or player 2's turn
   * @return the player whose turn it is
   */
  public int getCurrentTurn()
  {
      return currentTurn.get(); 
  }
  /**
   * change whose turn it is
   */
  public void setCurrentTurn(int newCurrentTurn)
  {
      currentTurn.set(newCurrentTurn);
  }
  /**
   * return the max value of turns available to players
   * @return max turns available
   */
  public int getMaxTurns()
  {
      return maxTurns;
  }
  /**
   * set max draw turns available
   * @param max num of draw turns
   */
  public void setMaxTurns(int newMaxTurns)
  {
      maxTurns = newMaxTurns;
  }
  /**
   * return the current Turn it is
   * @return IntegerProperty currentTurn
   */
  public IntegerProperty getTurnProperty()
  {
      return currentTurn; 
  }
  /**
   * get the total number of games played
   * @return total games played
   */
  public int getTotalGames()
  {
      return totalGames;
  }
}