package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.GameHashMap;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.GameWrapper.GameState;
import org.asSchool.ttt.dataModel.ontology.*;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


import org.asSchool.ttt.gameMaster.*;


public class GameMoveValidation extends OneShotBehaviour {


	private PutGameField putGameField = new PutGameField();
	private GameMasterAgent gameMasterAgent;
	private GameHashMap gameHashMap;
	
	
	public GameMoveValidation(GameMasterAgent myA, PutGameField putGameField) {
		// TODO Auto-generated constructor stub
		this.putGameField = putGameField;
		this.gameMasterAgent = myA;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void action() {
		
		this.gameHashMap = this.gameMasterAgent.getGameMasterBoardModel().getGameHashMap();
		
		//GetGameField getGameField = new GetGameField();
		Game oldGame = new Game();
		GameWrapper gameWrapper = new GameWrapper();
		
		
		GameBoard receivedGameBoard = new GameBoard();
		receivedGameBoard = putGameField.getGameBoard();
		
		AbstractMarkType[][] oldGameBoardArray = new AbstractMarkType[3][3];
		AbstractMarkType[][] newGameBoardArray = new AbstractMarkType[3][3];

		Cross cross = new Cross();
		Circle circle = new Circle();

		boolean isCross = false;
		boolean isCircle = false;
		
		int newMarkCnt = 0; // counts the amount of new marks. It should be one, otherwise the GameMove wasn´t valid.
		boolean CorrectGameMove = true;
		int changedGameRow = 0;
		int changedGameCol = 0;
		
		oldGame = gameHashMap.get(putGameField.getGameID());
		
		oldGameBoardArray = GameWrapper.transformToMarkArray(oldGame.getGameBoard());
		newGameBoardArray = GameWrapper.transformToMarkArray(receivedGameBoard);
		
		// Check if the new Move is valid (X remains X, O remains O and one previously empty field has to be filled by a mark)
		
		for(int col = 0; col <= 3; col++) {
			 for(int row = 0; row <= 3; row++) {
				 if (oldGameBoardArray[row][col] instanceof Cross)
				 {
					 if (! (newGameBoardArray[row][col] instanceof Cross)) {
						 CorrectGameMove = false;
						 break;
					 }
				 }
				 else if (oldGameBoardArray[row][col] instanceof Circle)
				 {
					 if (! (newGameBoardArray[row][col] instanceof Circle)) {
						 CorrectGameMove = false;
						 break;
					 }
				 }
				 else if (oldGameBoardArray[row][col]==null) {
					 if (newGameBoardArray[row][col] != null) {
						 newMarkCnt++;
						 changedGameRow = row;
						 changedGameCol = col;
						 if (newGameBoardArray[row][col] instanceof Cross) isCross = true;
						 if (newGameBoardArray[row][col] instanceof Circle) isCircle = true;
					 }
				 }
			 }
		}
		
		 if (CorrectGameMove == true && newMarkCnt == 1) {
			//Add new GameBoard to GameList
			 
			 gameHashMap.put(putGameField.getGameID(), oldGame);
			 
			 //set Game Move:
			 
			 GameMove gameMove = new GameMove();
			 if (isCross) gameMove.setMarkType(cross);
			 if (isCircle) gameMove.setMarkType(circle);
			 
			 gameMove.setGameID(oldGame.getGameID());
			 gameMove.setGameRow(changedGameRow);
			 gameMove.setGameColumn(changedGameCol);
			 oldGame.addGameMoveHistory(gameMove);

			 //Check whether game is still in progress or is already finished:
			 
			 GameWrapper gameWrapperResult = new GameWrapper (oldGame);
			 
			 switch(gameWrapperResult.getGameState()){
			 
				 case InProgress:  //Send the new GameBoard to the NextPlayer (dependend of the previous MarkType)
					 
					 if (isCross) {
							SendGameMoveToPlayer sendGameMoveToPlayer = new SendGameMoveToPlayer(oldGame.getGameID(), oldGame.getOMarkPlayer());
							this.myAgent.addBehaviour(sendGameMoveToPlayer);
							
						} else if (isCircle) {
							SendGameMoveToPlayer sendGameMoveToPlayer = new SendGameMoveToPlayer(oldGame.getGameID(), oldGame.getXMarkPlayer() );
							this.myAgent.addBehaviour(sendGameMoveToPlayer);
						}

					 break;
					 
				 case FinalizedRemis: //Send information about result to players +++ end game, delete game in gameList +++ store game in gamehistory
					 sendGameResult(null,oldGame);
					 gameHashMap.remove(oldGame.getGameID());
					 break;
				 case FinalizedWon: //Send information about result to players +++ end game, delete game in gameList +++ store game in gamehistory
					 sendGameResult(gameWrapper.getWinnerMark(),oldGame);
					 gameHashMap.remove(oldGame.getGameID());
					 break;
				 case InitialState: 
					 break;
			 }

		 }
		
	}
		
		
		
		// TODO Auto-generated method stub

	private void sendGameResult(AbstractMarkType winnerMark, Game finishedGame)
	{
		ACLMessage winnerMessage = new ACLMessage(); 
		ACLMessage loserMessage = new ACLMessage();
		ACLMessage remisMessage1 = new ACLMessage();
		ACLMessage remisMessage2 = new ACLMessage();
		
		winnerMessage.setLanguage(new SLCodec().getName());
		winnerMessage.setOntology(TicTacToeOntology.getInstance().getName());
		
		loserMessage.setLanguage(new SLCodec().getName());
		loserMessage.setOntology(TicTacToeOntology.getInstance().getName());
		
		remisMessage1.setLanguage(new SLCodec().getName());
		remisMessage1.setOntology(TicTacToeOntology.getInstance().getName());
		
		remisMessage2.setLanguage(new SLCodec().getName());
		remisMessage2.setOntology(TicTacToeOntology.getInstance().getName());
		
		GameResult gameResult = new GameResult();
		gameResult.setGameID(finishedGame.getGameID());
		
		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());		
		
		if (winnerMark != null) {
			if (winnerMark instanceof Cross) {
				winnerMessage.addReceiver(finishedGame.getXMarkPlayer().getAid()); 
				loserMessage.addReceiver(finishedGame.getOMarkPlayer().getAid());
				gameResult.setWinner(finishedGame.getXMarkPlayer());
				gameResult.setLoser(finishedGame.getOMarkPlayer());

				
			} else if (winnerMark instanceof Circle) {
				winnerMessage.addReceiver(finishedGame.getOMarkPlayer().getAid()); 
				loserMessage.addReceiver(finishedGame.getXMarkPlayer().getAid()); 
				gameResult.setWinner(finishedGame.getOMarkPlayer());
				gameResult.setLoser(finishedGame.getXMarkPlayer());
			}
			
			agentAction.setAction(gameResult);
			
			// --- Put the offer into the message -------------
			try {
				this.myAgent.getContentManager().fillContent(winnerMessage, agentAction);
			} catch (CodecException | OntologyException e) {
				System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
			}
			
			this.myAgent.send(winnerMessage);
			
			try {
				this.myAgent.getContentManager().fillContent(loserMessage, agentAction);
			} catch (CodecException | OntologyException e) {
				System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
			}
			
			this.myAgent.send(loserMessage);
			
		} else {
			
			remisMessage1.addReceiver(finishedGame.getXMarkPlayer().getAid()); 
			remisMessage2.addReceiver(finishedGame.getOMarkPlayer().getAid());
			gameResult.setRemisPlayer1(finishedGame.getXMarkPlayer());
			gameResult.setRemisPlayer2(finishedGame.getOMarkPlayer());
			
			agentAction.setAction(gameResult);
			
			// --- Put the offer into the message -------------
			try {
				this.myAgent.getContentManager().fillContent(remisMessage1, agentAction);
			} catch (CodecException | OntologyException e) {
				System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
			}
			
			this.myAgent.send(remisMessage1);
			
			try {
				this.myAgent.getContentManager().fillContent(remisMessage2, agentAction);
			} catch (CodecException | OntologyException e) {
				System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
			}
			
			this.myAgent.send(remisMessage2);
		}
	}
}
