package org.asSchool.ttt.agentPlayer;

import org.asSchool.ttt.agentPlayer.behaviour.MessageReceiveBehaviour;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

/**
 * The Class AbstractAgentPlayer.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public abstract class AbstractAgentPlayer extends Agent {

	private static final long serialVersionUID = -6887403126576418029L;

	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		
		this.addBehaviour(new MessageReceiveBehaviour(this));
	}

	/**
	 * Will be invoked if a message was received.
	 * @param aclMessage the ACLMessage to react on
	 */
	public abstract void onMessageReceived(ACLMessage aclMessage);
	
}
