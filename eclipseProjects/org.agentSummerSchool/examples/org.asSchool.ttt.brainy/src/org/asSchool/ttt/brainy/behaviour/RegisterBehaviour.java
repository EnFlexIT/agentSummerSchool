package org.asSchool.ttt.brainy.behaviour;

import org.asSchool.ttt.brainy.BrainyAgentPlayer;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Register;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RegisterBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = -5983918484647149434L;

	private BrainyAgentPlayer agentPlayer;
	
	public RegisterBehaviour(BrainyAgentPlayer agentPlayer) {
		this.agentPlayer = agentPlayer;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		AbstractPlayer abstractPlayer = new AbstractPlayer();
		abstractPlayer.setAid(this.agentPlayer.getAID());
		abstractPlayer.setScore(0);
		Register register = new Register();
		register.setPlayer(abstractPlayer);
		this.agentPlayer.sendMessageToGameMaster(ACLMessage.REQUEST, register);
	}
}
