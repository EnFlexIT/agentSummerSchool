package org.asSchool.ttt.gameMaster.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The Class MessageReceiveBehaviour.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class MessageReceiveBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 4748239182828378302L;

	
	/**
	 * Instantiates a new message receive behaviour.
	 * @param agent the instance of the agent
	 */
	public MessageReceiveBehaviour(Agent agent) {
		super(agent);
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
			AID senderAgent = aclMessage.getSender();
			
			// --- TODO to be continued ----
			
		}
	}

}
