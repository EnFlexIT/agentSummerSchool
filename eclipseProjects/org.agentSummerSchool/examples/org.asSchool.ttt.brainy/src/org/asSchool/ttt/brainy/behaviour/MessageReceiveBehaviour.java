package org.asSchool.ttt.brainy.behaviour;

import org.asSchool.ttt.brainy.BrainyAgentPlayer;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameResult;

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

	private static final long serialVersionUID = 1L;
	
	private BrainyAgentPlayer playerAgent;

	/**
	 * Instantiates a new message receive behaviour.
	 * @param playerAgent the player agent
	 */
	public MessageReceiveBehaviour(BrainyAgentPlayer playerAgent) {
		super();
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
				
			} catch (CodecException | OntologyException ex) {
				ex.printStackTrace();
			}
			
			if (agentAction.getAction() instanceof GameResult) {
				this.myAgent.addBehaviour(new RegisterBehaviour((BrainyAgentPlayer) this.myAgent));
			} else  if (agentAction.getAction() instanceof GameAction) {
				this.myAgent.addBehaviour(new DecisionBehaviour(playerAgent, (GameAction) agentAction.getAction()));
			}
		}			
		
	}

}
