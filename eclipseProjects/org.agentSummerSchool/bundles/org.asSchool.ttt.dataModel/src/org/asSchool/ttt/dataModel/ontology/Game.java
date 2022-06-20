package org.asSchool.ttt.dataModel.ontology;

import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Game
* @author ontology bean generator
* @version 2022/06/20, 23:26:38
*/
public class Game extends AbstractGameConcepts{ 

   /**
* Protege name: gameMoveHistory
   */
   private List gameMoveHistory = new ArrayList();
   public void addGameMoveHistory(GameMove elem) { 
     List oldList = this.gameMoveHistory;
     gameMoveHistory.add(elem);
   }
   public boolean removeGameMoveHistory(GameMove elem) {
     List oldList = this.gameMoveHistory;
     boolean result = gameMoveHistory.remove(elem);
     return result;
   }
   public void clearAllGameMoveHistory() {
     List oldList = this.gameMoveHistory;
     gameMoveHistory.clear();
   }
   public Iterator getAllGameMoveHistory() {return gameMoveHistory.iterator(); }
   public List getGameMoveHistory() {return gameMoveHistory; }
   public void setGameMoveHistory(List l) {gameMoveHistory = l; }

   /**
* Protege name: gameBoard
   */
   private GameBoard gameBoard;
   public void setGameBoard(GameBoard value) { 
    this.gameBoard=value;
   }
   public GameBoard getGameBoard() {
     return this.gameBoard;
   }

   /**
* Protege name: xMarkPlayer
   */
   private AbstractPlayer xMarkPlayer;
   public void setXMarkPlayer(AbstractPlayer value) { 
    this.xMarkPlayer=value;
   }
   public AbstractPlayer getXMarkPlayer() {
     return this.xMarkPlayer;
   }

   /**
* Protege name: oMarkPlayer
   */
   private AbstractPlayer oMarkPlayer;
   public void setOMarkPlayer(AbstractPlayer value) { 
    this.oMarkPlayer=value;
   }
   public AbstractPlayer getOMarkPlayer() {
     return this.oMarkPlayer;
   }

}
