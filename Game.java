import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty; 
/**
* This class will contain the rules of the game, instantiate the players and map, and deal with how the 
* graphics should be set up. 
* @author D. Chen
* @version b1.0
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
  //move drawphase and things later to here and use a listener in maingamegui class to change 
  //start drawphase at end of instantiation of maingamegui -> set drawphase boolean to start
  //increment lines creawted - once it changes change the turn
  //once lines created hits 5 end draw phase
  //once draw phase ends, start combat phase
  //event driven progrmaming makes me want to choke someone 
  //I don't think we need getters and setterrs for this. Endgame doesn't need a setter, endmatch doesn't,
  //numWins and totalGames are pretty much the same imo and we should onl need one. 
  //intermission - property for endGame 
  //get winner and set current turn to that depending on winner
  //start cursorTimer
  //add only cursor for player back 
  //end phase after line created
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
      maxTurns = 5;
      
  }
  public void refreshGameState()
  {
      player1.setHealth(3);
      player2.setHealth(3);
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
  public BooleanProperty getEndGameProperty()
  {
      return endGame; 
  }
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
      if (player1.isDead()) //give the win to the player who won 
        player2.addWin();
      else
        player1.addWin();
    }
  }
  
  /**
  * Returns the winner of the game.
  * @return The winner of the game
  **/
  public int getWinner()
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
          if (player1.getWins() * 2 > getTotalGames())
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
  public void setWinnerId(String player)
  {
      winnerId = player.substring(6);
  }
  public String getWinnerId()
  {
      return winnerId;
  }
  public void setDrawPhase(boolean value)
  {
      drawPhase.set(value); 
  }
  public BooleanProperty getDrawProperty()
  {
      return drawPhase;
  }
  public boolean getDrawPhase()
  {
      return drawPhase.get(); 
  }
  public void incrementLine()
  {
      turnCount++;
      if(turnCount % 2 == 0)
          currentTurn.set(2); 
      else
          currentTurn.set(1); 
      if(turnCount >= maxTurns)
          //end drawPhase
          setDrawPhase(false);
  }
  public int getCurrentTurn()
  {
      return currentTurn.get(); 
  }
  public void setCurrentTurn(int newCurrentTurn)
  {
      currentTurn.set(newCurrentTurn);
  }
  public int getMaxTurns()
  {
      return maxTurns;
  }
  public void setMaxTurns(int newMaxTurns)
  {
      maxTurns = newMaxTurns;
  }
  public IntegerProperty getTurnProperty()
  {
      return currentTurn; 
  }
  public int getTotalGames()
  {
      return totalGames;
  }
}