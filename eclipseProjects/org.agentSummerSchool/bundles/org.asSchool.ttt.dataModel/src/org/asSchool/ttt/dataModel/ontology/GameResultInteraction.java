package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameResultInteraction
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class GameResultInteraction implements AgentAction {

   /**
* Protege name: gameResult
   */
   private GameResult gameResult;
   public void setGameResult(GameResult value) { 
    this.gameResult=value;
   }
   public GameResult getGameResult() {
     return this.gameResult;
   }

}
