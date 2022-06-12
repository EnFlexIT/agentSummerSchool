package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameFieldInteraction
* @author ontology bean generator
* @version 2022/06/13, 01:04:42
*/
public class GameFieldInteraction implements AgentAction {

   /**
* Protege name: gameField
   */
   private Game gameField;
   public void setGameField(Game value) { 
    this.gameField=value;
   }
   public Game getGameField() {
     return this.gameField;
   }

}
