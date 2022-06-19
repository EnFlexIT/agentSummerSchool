package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.ontology.*;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendGameMoveToPlayer extends OneShotBehaviour {
	
	GameBoard GameBoard = new GameBoard();
	AbstractPlayer nextPlayer = new AbstractPlayer();
	int gameID;
	
	public SendGameMoveToPlayer(GameBoard gameBoard,int gameID, AbstractPlayer nextPlayer) {
		this.GameBoard = gameBoard;
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
		
		PutGameField putGameField = new PutGameField ();
		putGameField.setGameBoard(this.GameBoard);

		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());
		agentAction.setAction(putGameField);
		
		// --- Put the offer into the message -------------
		try {
			this.myAgent.getContentManager().fillContent(gameMessage, agentAction);
		} catch (CodecException | OntologyException e) {
			System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
		}
		
		this.myAgent.send(gameMessage);
	}

}
