package org.asSchool.ttt.agentPlayerExample;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;
import org.asSchool.ttt.agentPlayer.behaviour.MessageReceiveBehaviour;
import org.asSchool.ttt.agentPlayer.behaviour.RegisterBehaviour;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.AgentPlayer;
import org.asSchool.ttt.dataModel.ontology.Register;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.lang.acl.ACLMessage;

public class AgentPlayerExample extends AbstractAgentPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {
		
		this.getContentManager().registerLanguage(this.getSlCodec());
		this.getContentManager().registerOntology(this.getTicTacToeOntology());
		// --- Add message receive behaviour ------------------------
		this.addBehaviour(new MessageReceiveBehaviour(this));
		this.addBehaviour(new RegisterBehaviour(this));
		
		Register register = new Register();
		AgentPlayer agentPlayer = new AgentPlayer();
		agentPlayer.setAid(this.getAID());
		register.setPlayer(agentPlayer);
		this.sendMessageToGameMaster(ACLMessage.REQUEST, register);
	
	}
	
	@Override
	public void sendMessageToGameMaster(int performative, Concept concept) {

		// --- Define the message -----------------------------------
		ACLMessage msg = new ACLMessage(performative);
		msg.setSender(this.getAID());
		msg.addReceiver(this.getGameMasterAID());
		msg.setLanguage(this.getSlCodec().getName());
		msg.setOntology(this.getTicTacToeOntology().getName());
		
		// --- Define Action ----------------------------------------
		Action act = new Action();
		act.setActor(this.getAID());
		act.setAction(concept);
		
		try {
			this.getContentManager().fillContent(msg, act);
			this.send(msg);
			
		} catch (CodecException | OntologyException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public AbstractPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onMessageReceived(AgentAction agentAction) {
		// TODO Auto-generated method stub
		
	}

}
