package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: RegisterInteraction
* @author ontology bean generator
* @version 2022/06/20, 23:26:38
*/
public class RegisterInteraction implements AgentAction {

   /**
* Protege name: player
   */
   private AbstractPlayer player;
   public void setPlayer(AbstractPlayer value) { 
    this.player=value;
   }
   public AbstractPlayer getPlayer() {
     return this.player;
   }

}
