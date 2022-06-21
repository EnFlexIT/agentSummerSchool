package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


/**
 * The Class SendGameMoveToPlayer.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class SendGameMoveToPlayer extends OneShotBehaviour {
	
	private static final long serialVersionUID = -2149123971979452430L;

	private AbstractPlayer nextPlayer;
	private Game game;
	
	/**
	 * Instantiates a new send game move to player.
	 *
	 * @param gameID the game ID
	 * @param nextPlayer the next player
	 */
	public SendGameMoveToPlayer(Game game, AbstractPlayer nextPlayer) {
		this.game = game;
		this.nextPlayer = nextPlayer;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		
		GameAction gameAction = new GameAction();
		gameAction.setGame(this.game);
		
		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());
		agentAction.setAction(gameAction);
		
		ACLMessage gameMessage = new ACLMessage(ACLMessage.INFORM);
		gameMessage.addReceiver(this.nextPlayer.getAid()); 
		gameMessage.setLanguage(new SLCodec().getName());
		gameMessage.setOntology(TicTacToeOntology.getInstance().getName());
		
		// --- Put the Game into the message -------------
		try {
			this.myAgent.getContentManager().fillContent(gameMessage, agentAction);
			this.myAgent.send(gameMessage);

		} catch (CodecException | OntologyException e) {
			System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
		}
	}

}
