package org.asSchool.ttt.agentPlayerExample.behaviour;

import org.asSchool.ttt.agentPlayerExample.AgentPlayerExample;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.Circle;
import org.asSchool.ttt.dataModel.ontology.Cross;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameBoard;
import org.asSchool.ttt.dataModel.ontology.GameRow;

import jade.content.Concept;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class GetGameActionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	AgentPlayerExample playerAgent;
	GameAction gameAction = new GameAction();
	
	public GetGameActionBehaviour(AgentPlayerExample playerAgent, GameAction action) {
		this.playerAgent = playerAgent;
		this.gameAction = action;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		Game currentGame = this.gameAction.getGame();
		GameBoard currentGameBoard = currentGame.getGameBoard();
		
		currentGameBoard = strategicGameMove(currentGameBoard, getMarkType(currentGame));
		currentGame.setGameBoard(currentGameBoard);
		this.gameAction.setGame(currentGame);	
		this.playerAgent.sendMessageToGameMaster(ACLMessage.INFORM, this.gameAction);

	}
	
	private GameBoard strategicGameMove (GameBoard currentGameBoard, AbstractMarkType markType) {

		AbstractMarkType[][] markArray = GameWrapper.transformToMarkArray(currentGameBoard);
		for (int col = 0; col<3; col++) {
			
			for (int row = 0; row<3; row++) {
				if (markArray[row][col] == null) {
					//set a mark in the first free field of the gameBoard
					markArray[row][col] = markType;
				}
					
			}
		}
		
		return updatedGameBoard(currentGameBoard, markArray);
		
		
	}
	
	private AbstractMarkType getMarkType (Game currentGame) {
		Circle circle = new Circle();
		Cross cross = new Cross();
		
		if (currentGame.getOMarkPlayer().getAid() == this.playerAgent.getAID()) {
			return circle;
			
		} else if (currentGame.getXMarkPlayer().getAid() == this.playerAgent.getAID()) {
			return cross;
			
		} else {
			return null;
		}

	}
	
	private GameBoard updatedGameBoard (GameBoard gameBoard, AbstractMarkType[][] markArray) {

		
		GameRow gameRow1 = new GameRow();
		GameRow gameRow2 = new GameRow();
		GameRow gameRow3 = new GameRow();
		
		gameRow1.setColumn1(markArray[0][0]);
		gameRow1.setColumn2(markArray[0][1]);
		gameRow1.setColumn2(markArray[0][2]);
		
		gameRow2.setColumn1(markArray[1][0]);
		gameRow2.setColumn2(markArray[1][1]);
		gameRow2.setColumn2(markArray[1][2]);
		
		gameRow3.setColumn1(markArray[2][0]);
		gameRow3.setColumn2(markArray[2][1]);
		gameRow3.setColumn2(markArray[2][2]);
		
		gameBoard.setGameRow1(gameRow1);
		gameBoard.setGameRow2(gameRow2);
		gameBoard.setGameRow3(gameRow3);
		
		return gameBoard;
		
	}

}
