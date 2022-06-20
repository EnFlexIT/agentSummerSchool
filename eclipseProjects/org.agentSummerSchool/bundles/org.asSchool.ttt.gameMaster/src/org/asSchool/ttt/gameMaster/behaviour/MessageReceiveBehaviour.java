package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.ontology.*;
import org.asSchool.ttt.gameMaster.GameMasterAgent;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

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
			
			Action contentAction = null;
			try {
				contentAction = (Action) this.myAgent.getContentManager().extractContent(aclMessage);
			} catch (CodecException | OntologyException e1) {
				e1.printStackTrace();
			}
			
			if (contentAction.getAction() instanceof Register) {
				RegisterationReceiveBehaviour registerationReceiveBehaviour = new RegisterationReceiveBehaviour((GameMasterAgent) myAgent, (Register) contentAction.getAction(), aclMessage.getSender());
				myAgent.addBehaviour(registerationReceiveBehaviour);
				
			} else if (contentAction.getAction() instanceof PutGameField) {
				GameMoveValidation gameMoveValidation = new GameMoveValidation((GameMasterAgent) myAgent, (PutGameField) contentAction.getAction());
				myAgent.addBehaviour(gameMoveValidation);
			}
			
		}
	}

}
