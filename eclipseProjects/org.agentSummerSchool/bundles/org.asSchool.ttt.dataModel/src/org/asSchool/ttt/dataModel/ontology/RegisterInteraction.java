package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: RegisterInteraction
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class RegisterInteraction implements AgentAction {

   /**
* Protege name: agentPlayer
   */
   private AgentPlayer agentPlayer;
   public void setAgentPlayer(AgentPlayer value) { 
    this.agentPlayer=value;
   }
   public AgentPlayer getAgentPlayer() {
     return this.agentPlayer;
   }

}
