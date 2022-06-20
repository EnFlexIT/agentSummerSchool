package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.GameHashMap;
import org.asSchool.ttt.dataModel.ontology.*;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendGameMoveToPlayer extends OneShotBehaviour {
	
	private AbstractPlayer nextPlayer = new AbstractPlayer();
	private int gameID;
	private GameHashMap gameHashMap;
	
	public SendGameMoveToPlayer(int gameID, AbstractPlayer nextPlayer) {
		this.nextPlayer = nextPlayer;
		this.gameID = gameID;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		ACLMessage gameMessage = new ACLMessage();
		gameMessage.addReceiver(this.nextPlayer.getAid()); 
		gameMessage.setLanguage(new SLCodec().getName());
		gameMessage.setOntology(TicTacToeOntologyOntology.getInstance().getName());
		
		GetGameField getGameField = new GetGameField ();
		getGameField.setGameBoard(this.gameHashMap.get(this.gameID).getGameBoard());

		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());
		agentAction.setAction(getGameField);
		
		// --- Put the offer into the message -------------
		try {
			this.myAgent.getContentManager().fillContent(gameMessage, agentAction);
		} catch (CodecException | OntologyException e) {
			System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
		}
		
		this.myAgent.send(gameMessage);
	}

}
