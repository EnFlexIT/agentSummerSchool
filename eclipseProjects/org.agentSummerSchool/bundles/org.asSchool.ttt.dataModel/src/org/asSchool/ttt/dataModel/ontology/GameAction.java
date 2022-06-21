package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameAction
* @author ontology bean generator
* @version 2022/06/21, 19:18:37
*/
public class GameAction implements AgentAction {

   /**
* Protege name: game
   */
   private Game game;
   public void setGame(Game value) { 
    this.game=value;
   }
   public Game getGame() {
     return this.game;
   }

}
