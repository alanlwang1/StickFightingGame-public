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
  private int numWins;
  private int totalGames; 
  private Player player1, player2; 
  /**
  * @summary Checks to see if the game is over, or if one player is dead. 
  * @return if one player is dead (true if so, false otherwise)
  **/
  public boolean gameState() 
  {
    return !endGame;
  }
  
  public void checkWinCondition()
  {
    if 
  }
}
