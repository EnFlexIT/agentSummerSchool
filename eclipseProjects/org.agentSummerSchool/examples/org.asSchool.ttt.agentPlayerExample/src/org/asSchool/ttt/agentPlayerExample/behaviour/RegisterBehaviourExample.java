package org.asSchool.ttt.agentPlayerExample.behaviour;

import org.asSchool.ttt.agentPlayerExample.AgentPlayerExample;
import org.asSchool.ttt.dataModel.ontology.Register;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RegisterBehaviourExample extends OneShotBehaviour {

	private static final long serialVersionUID = -5983918484647149434L;

	private AgentPlayerExample agentPlayer;
	
	public RegisterBehaviourExample(AgentPlayerExample agentPlayer) {
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
