/**
* @summary This class will contain the rules of the game, instantiate the players and map, and deal with how the 
* graphics should be set up. 
* @author D. Chen
* @created 05-17-18
* @modified 05-17-18
**/
public abstract class Game
{
  //instantiate the variables needed for the game
  private boolean endGame = false;
  private boolean endMatch = false;
  private int numWins;
  private int totalGames; 
  private Player player1, player2; 
  
  /**
  * @summary Checks to see if the game is over.
  * @return if game is over (true if so, false otherwise)
  **/
  public boolean gameState() 
  {
    return !endGame;
  }
  
  /**
  * @summary Checks to see if the match is over.
  * @return if match is over (true if so, false otherwise)
  **/
  public boolean matchState() 
  {
    return !endMatch;
  }
  
  /**
  * @summary Checks if one player is dead. 
  **/
  public void checkWinCondition()
  {
    if (player1.isDead() || player2.isDead())
    {  
      endGame = true;
     
    }
  }
  
  /**
  * @summary returns the winner of the game.
  * @return the winner of the game.
  **/
  public Player getWinner()
  {
    if (player1.isDead())
      return player2;
    else 
    {  
      if (player2.isDead())
        return player1;
    }
    return null;
  }
  
}
