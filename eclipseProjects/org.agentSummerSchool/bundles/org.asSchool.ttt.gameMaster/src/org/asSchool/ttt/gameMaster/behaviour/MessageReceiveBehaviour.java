package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.Register;
import org.asSchool.ttt.gameMaster.GameMasterAgent;

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

	private GameMasterAgent gameMasterAgent;
	
	/**
	 * Instantiates a new message receive behaviour.
	 * @param agent the instance of the agent
	 */
	public MessageReceiveBehaviour(GameMasterAgent gameMasterAgent) {
		super(gameMasterAgent);
		this.gameMasterAgent = gameMasterAgent;
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
				contentAction = (Action) this.gameMasterAgent.getContentManager().extractContent(aclMessage);
			} catch (CodecException | OntologyException e1) {
				e1.printStackTrace();
			}
			
			if (contentAction.getAction() instanceof Register) {
				this.gameMasterAgent.addBehaviour(new RegistrationReceiveBehaviour(this.gameMasterAgent, (Register) contentAction.getAction(), aclMessage.getSender()));
				
			} else if (contentAction.getAction() instanceof GameAction) {
				this.gameMasterAgent.addBehaviour(new GameMoveValidation(this.gameMasterAgent, (GameAction) contentAction.getAction()));
			}
			
		}
	}

}
