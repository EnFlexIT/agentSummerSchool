package org.asSchool.ttt.agentPlayerExample;


import org.asSchool.ttt.agentPlayerExample.behaviour.MessageReceiveBehaviourExample;
import org.asSchool.ttt.agentPlayerExample.behaviour.RegisterBehaviourExample;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.AgentPlayer;
import org.asSchool.ttt.dataModel.ontology.Register;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;

import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.OntoAID;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentPlayerExample extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AID gameMasterAID;
	private SLCodec slCodec;

	protected void setup() {
		
		this.getContentManager().registerLanguage(this.getSlCodec());
		this.getContentManager().registerOntology(this.getTicTacToeOntology());
		// --- Add message receive behaviour ------------------------
		this.addBehaviour(new MessageReceiveBehaviourExample(this));
		this.addBehaviour(new RegisterBehaviourExample(this));
	
	}
	
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

	public AbstractPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onMessageReceived(AgentAction agentAction) {
		// TODO Auto-generated method stub
		
	}
	
	protected Ontology getTicTacToeOntology() {
		return TicTacToeOntology.getInstance();
	}
	
	public SLCodec getSlCodec() {
		if (slCodec==null) {
			slCodec = new SLCodec();
		}
		return slCodec;
	}
	
	protected AID getGameMasterAID() {
		if (gameMasterAID==null) {
			Object[] startArgs = this.getArguments();
			gameMasterAID = (OntoAID) startArgs[0];
			//AID newAID = new AID ("GaMaAg", AID.ISLOCALNAME);
			//gameMasterAID = newAID;
			
		}
		return gameMasterAID;
	}

}
