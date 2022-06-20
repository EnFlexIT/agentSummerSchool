package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameFieldInteraction
* @author ontology bean generator
* @version 2022/06/20, 20:33:52
*/
public class GameFieldInteraction implements AgentAction {

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
* Protege name: gameID
   */
   private int gameID;
   public void setGameID(int value) { 
    this.gameID=value;
   }
   public int getGameID() {
     return this.gameID;
   }

}
