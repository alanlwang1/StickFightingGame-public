/**
* This class will contain the rules of the game, instantiate the players and map, and deal with how the 
* graphics should be set up. 
* @author D. Chen
* @version b1.0
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
  * Checks to see if the game is over.
  * @return If game is over (true if so, false otherwise)
  **/
  public abstract boolean gameState();
  
  /**
  * Checks to see if the match is over.
  * @return If match is over (true if so, false otherwise)
  **/
  public abstract boolean matchState();
  
  /**
  * Checks if one player is dead. Adds a win to the players who won, if necessary. 
  **/
  public void checkWinCondition()
  {
    if (player1.isDead() || player2.isDead()) //if either player is dead
    {  
      endGame = true; //end the game
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
