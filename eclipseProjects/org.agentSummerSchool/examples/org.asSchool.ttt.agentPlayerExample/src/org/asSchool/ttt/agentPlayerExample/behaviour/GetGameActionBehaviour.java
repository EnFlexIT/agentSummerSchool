package org.asSchool.ttt.agentPlayerExample.behaviour;

import org.asSchool.ttt.agentPlayerExample.AgentPlayerExample;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameBoard;
import org.asSchool.ttt.dataModel.ontology.GameRow;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class GetGameActionBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AgentPlayerExample playerAgent;
	private GameAction gameAction = new GameAction();
	private GameWrapper gameWrapper;
	
	public GetGameActionBehaviour(AgentPlayerExample playerAgent, GameAction action) {
		this.playerAgent = playerAgent;
		this.gameAction = action;
	}

	@Override
	public void action() {
		
		Game currentGame = this.gameAction.getGame();
		GameBoard currentGameBoard = currentGame.getGameBoard();
		
		this.getGameWrapper().setGame(currentGame);
		currentGameBoard = strategicGameMove(currentGameBoard, this.getGameWrapper().getMark(this.playerAgent.getAID()));
		currentGame.setGameBoard(currentGameBoard);
		this.gameAction.setGame(currentGame);	
		this.playerAgent.sendMessageToGameMaster(ACLMessage.INFORM, this.gameAction);

	}
	
	private GameWrapper getGameWrapper() {
		if (gameWrapper==null) {
			gameWrapper = new GameWrapper();
		}
		return gameWrapper;
	}
	
	private GameBoard strategicGameMove(GameBoard currentGameBoard, AbstractMarkType markType) {
		int col =0;
		int row =0;
		AbstractMarkType[][] markArray = GameWrapper.transformToMarkArray(currentGameBoard);
		for (col = 0; col<3; col++) {
			
			for (row = 0; row<3; row++) {
				if (markArray[row][col] == null) {
					//set a mark in the first free field of the gameBoard
					markArray[row][col] = markType;
					
					this.getGameWrapper().setMark(row+1, col+1, markType);
					return updatedGameBoard(currentGameBoard, markArray);
				}
					
			}
		}
		
		this.getGameWrapper().setMark(row+1, col+1, markType);
		return updatedGameBoard(currentGameBoard, markArray);
		
		
	}
	
	
	private GameBoard updatedGameBoard (GameBoard gameBoard, AbstractMarkType[][] markArray) {

		
		GameRow gameRow1 = new GameRow();
		GameRow gameRow2 = new GameRow();
		GameRow gameRow3 = new GameRow();
		
		gameRow1.setColumn1(markArray[0][0]);
		gameRow1.setColumn2(markArray[0][1]);
		gameRow1.setColumn3(markArray[0][2]);
		
		gameRow2.setColumn1(markArray[1][0]);
		gameRow2.setColumn2(markArray[1][1]);
		gameRow2.setColumn3(markArray[1][2]);
		
		gameRow3.setColumn1(markArray[2][0]);
		gameRow3.setColumn2(markArray[2][1]);
		gameRow3.setColumn3(markArray[2][2]);
		
		gameBoard.setGameRow1(gameRow1);
		gameBoard.setGameRow2(gameRow2);
		gameBoard.setGameRow3(gameRow3);
		
		return gameBoard;
		
	}

}
