package org.asSchool.ttt.agentPlayer.behaviour;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The Class MessageReceiveBehaviour.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class MessageReceiveBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 4748239182828378302L;

	private AbstractAgentPlayer playerAgent;
	
	/**
	 * Instantiates a new message receive behaviour.
	 * @param playerAgent the player agent
	 */
	public MessageReceiveBehaviour(AbstractAgentPlayer playerAgent) {
		super(playerAgent);
		this.playerAgent = playerAgent;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		
		ACLMessage aclMessage = this.getAgent().receive();
		if (aclMessage==null) {
			// --- Block till next message arrives ------------------
			this.block();
			
		} else {
			// --- Work on the current message ----------------------
			this.playerAgent.onMessageReceived(aclMessage);
		}
	}

}