package org.asSchool.ttt.agentPlayer;

import org.asSchool.ttt.agentPlayer.behaviour.MessageReceiveBehaviour;
import org.asSchool.ttt.agentPlayer.behaviour.RegisterBehaviour;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;

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

/**
 * The Class AbstractAgentPlayer.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public abstract class AbstractAgentPlayer extends Agent {

	private static final long serialVersionUID = -6887403126576418029L;

	private AID gameMasterAID;
	private SLCodec slCodec;
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		// --- Register Codec & Ontology ----------------------------
		this.getContentManager().registerLanguage(this.getSlCodec());
		this.getContentManager().registerOntology(this.getTicTacToeOntology());
		// --- Add message receive behaviour ------------------------
		this.addBehaviour(new MessageReceiveBehaviour(this));
		this.addBehaviour(new RegisterBehaviour(this));
	}

	/**
	 * Return the {@link SLCodec} to be used for message transfer.
	 * @return the SLCodec to be used for communication
	 */
	public SLCodec getSlCodec() {
		if (slCodec==null) {
			slCodec = new SLCodec();
		}
		return slCodec;
	}
	/**
	 * Returns the TicTacToe ontology .
	 * @return the ontology to be used for communication
	 */
	protected Ontology getTicTacToeOntology() {
		return TicTacToeOntology.getInstance();
	}
	
	/**
	 * Has to return the player representation for the current agent.
	 * @return the player instance of the current agent
	 */
	public abstract AbstractPlayer getPlayer();
	
	/**
	 * Returns the game master AID as configured in the start configuration of an agent setup.
	 * @return the game master AID
	 */
	protected AID getGameMasterAID() {
		if (gameMasterAID==null) {
			Object[] startArgs = this.getArguments();
			gameMasterAID = (OntoAID) startArgs[0];
		}
		return gameMasterAID;
	}
	
	/**
	 * Sends the specified concept as an AgentAction to the game master agent, using the 
	 * specified performative for the ACL message.
	 *
	 * @param performative the performative
	 * @param concept the concept
	 */
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
	
	
	/**
	 * Will be invoked if a message was received.
	 * @param aclMessage the ACLMessage to react on
	 */
	public abstract void onMessageReceived(ACLMessage aclMessage);
	
}
