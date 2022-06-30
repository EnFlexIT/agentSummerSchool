package org.asSchool.ttt.agentPlayerExample.behaviour;

import org.asSchool.ttt.agentPlayerExample.AgentPlayerExample;
import org.asSchool.ttt.dataModel.ontology.GameAction;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class MessageReceiveBehaviourExample extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AgentPlayerExample playerAgent;

	public MessageReceiveBehaviourExample(AgentPlayerExample playerAgent) {
		super();
		this.playerAgent = playerAgent;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
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
			
			if (agentAction.getAction() instanceof GameAction) {
				this.myAgent.addBehaviour(new GetGameActionBehaviour(playerAgent, (GameAction) agentAction.getAction()));
			}
		}	
		
		
	}
	
	
	

}
