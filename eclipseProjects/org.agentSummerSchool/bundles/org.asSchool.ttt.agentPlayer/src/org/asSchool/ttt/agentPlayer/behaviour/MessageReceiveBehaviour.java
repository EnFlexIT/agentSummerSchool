package org.asSchool.ttt.agentPlayer.behaviour;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
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
		
		Action agentAction = new Action();
		ACLMessage aclMessage = this.getAgent().receive();
		if (aclMessage==null) {
			// --- Block till next message arrives ------------------
			this.block();
			
		} else {
			// --- Work on the current message ----------------------
			try {
				agentAction = (Action) this.playerAgent.getContentManager().extractContent(aclMessage);
				this.playerAgent.onMessageReceived(agentAction);
				
			} catch (CodecException | OntologyException ex) {
				ex.printStackTrace();
			}
		}
	}

}
