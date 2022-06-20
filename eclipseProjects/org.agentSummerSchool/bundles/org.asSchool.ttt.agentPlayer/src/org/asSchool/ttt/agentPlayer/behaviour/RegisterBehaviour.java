package org.asSchool.ttt.agentPlayer.behaviour;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;
import org.asSchool.ttt.dataModel.ontology.Register;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The Class RegisterBehaviour represents a OneShotBehaviour that will register the 
 * current agent at the Game Master Agent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class RegisterBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -5983918484647149434L;

	private AbstractAgentPlayer agentPlayer;
	
	public RegisterBehaviour(AbstractAgentPlayer agentPlayer) {
		this.agentPlayer = agentPlayer;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		Register register = new Register();
		register.setPlayer(this.agentPlayer.getPlayer());
		this.agentPlayer.sendMessageToGameMaster(ACLMessage.REQUEST, register);
	}

}
