package org.asSchool.ttt.humanPlayer;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;

import jade.lang.acl.ACLMessage;

/**
 * The Class HumanPlayerAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class HumanPlayerAgent extends AbstractAgentPlayer { 

	private static final long serialVersionUID = -3300871772757135436L;

	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		// --- TODO Do some initialization ----------------
	}

	/* (non-Javadoc)
	 * @see org.asSchool.ttt.agentPlayer.AbstractAgentPlayer#onMessageReceived(jade.lang.acl.ACLMessage)
	 */
	@Override
	public void onMessageReceived(ACLMessage aclMessage) {
		// --- TODO Act on the received Message ----------- 
		
	}
	
}
