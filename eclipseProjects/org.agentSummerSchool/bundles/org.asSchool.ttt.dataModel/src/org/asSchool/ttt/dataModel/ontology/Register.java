package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Register
* @author ontology bean generator
* @version 2022/06/21, 19:18:37
*/
public class Register implements AgentAction {

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
