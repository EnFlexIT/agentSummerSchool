package org.asSchool.ttt.gameMaster.behaviour;

import java.util.List;

import org.asSchool.ttt.dataModel.GameHashMap;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Circle;
import org.asSchool.ttt.dataModel.ontology.Cross;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameLost;
import org.asSchool.ttt.dataModel.ontology.GameMove;
import org.asSchool.ttt.dataModel.ontology.GameRemis;
import org.asSchool.ttt.dataModel.ontology.GameResult;
import org.asSchool.ttt.dataModel.ontology.GameWon;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;
import org.asSchool.ttt.gameMaster.GameMasterAgent;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;


public class GameMoveValidation extends OneShotBehaviour {

	private static final long serialVersionUID = -7222793667703958346L;
	
	private GameMasterAgent gameMasterAgent;
	private GameHashMap gameHashMap;
	private GameAction gameAction;
	private GameMove gm = new GameMove();
	
	
	/**
	 * Instantiates a new game move validation.
	 *
	 * @param gameMasterAgent the game master agent
	 * @param gameAction the put game field
	 */
	public GameMoveValidation(GameMasterAgent gameMasterAgent, GameAction gameAction) {
		super(gameMasterAgent);
		this.gameMasterAgent = gameMasterAgent;
		this.gameAction = gameAction;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		 
		this.myAgent.addBehaviour(new TimerBehaviour(this.gameMasterAgent, 500000));
		this.gameHashMap = this.gameMasterAgent.getGameMasterBoardModel().getGameHashMap();
		
		//GetGameField getGameField = new GetGameField();
		Game newGame = this.gameAction.getGame();
		Game oldGame = this.gameMasterAgent.getGameMasterBoardModel().getGameHashMap().get(newGame.getGameID());
		Cross cross = new Cross();
		Circle circle = new Circle();
		
		GameWrapper gameWrapperNew = new GameWrapper(newGame);
		
		AbstractMarkType[][] newGameBoardArray = newGame!=null ?  GameWrapper.transformToMarkArray(newGame.getGameBoard()) : null;
		AbstractMarkType[][] oldGameBoardArray = oldGame!=null ? GameWrapper.transformToMarkArray(oldGame.getGameBoard())  : null;
		
		boolean isCross = false;
		boolean isCircle = false;
		
		int newMarkCnt = 0; // counts the amount of new marks. It should be one, otherwise the GameMove wasn´t valid.
		boolean correctGameMove = true;
		
		
		if (oldGameBoardArray!=null) {


			for (int col = 0; col<3; col++) {
				for (int row = 0; row<3; row++) {
					if (oldGameBoardArray[row][col] instanceof Cross) {
						// ---
						if (!(newGameBoardArray[row][col] instanceof Cross)) {
							correctGameMove = false;
							break;
						}
						
					} else if (oldGameBoardArray[row][col] instanceof Circle) {
						// ---
						if (!(newGameBoardArray[row][col] instanceof Circle)) {
							correctGameMove = false;
							break;
						}
						
					} else if (oldGameBoardArray[row][col] == null) {
						// ---
						if (newGameBoardArray[row][col] != null) {
							newMarkCnt++;
							if (newGameBoardArray[row][col] instanceof Cross) {
								isCross = true;
								gm.setGameColumn(col);
								gm.setGameRow(row);
								gm.setGameID(newGame.getGameID());
								gm.setMarkType(cross);
							}
							if (newGameBoardArray[row][col] instanceof Circle) {
								isCircle = true;
								gm.setGameColumn(col);
								gm.setGameRow(row);
								gm.setGameID(newGame.getGameID());
								gm.setMarkType(circle);
							}
						}
					}
					
				}
			}
		}
		
		
		if (correctGameMove == true && newMarkCnt == 1) {
			// Add new GameBoard to GameList
			this.gameMasterAgent.getGameMasterBoardModel().getGameHashMap().put(newGame.getGameID(), newGame);
			
			if (newGame.getGameMoveHistory().size()==0) {
				newGame.addGameMoveHistory(gm);
				
				gameWrapperNew.setGame(newGame);
			}
			switch (gameWrapperNew.getGameState()) {
			case InitialState:
				// --- Should never happen ---
				if (isCross) {
					this.myAgent.addBehaviour(new SendGameMoveToPlayer(newGame, newGame.getOMarkPlayer()));
				} else if (isCircle) {
					this.myAgent.addBehaviour(new SendGameMoveToPlayer(newGame, newGame.getXMarkPlayer()));
				}
				break;
				
			case InProgress: // Send the new GameBoard to the NextPlayer (dependend of the previous MarkType)
				if (isCross) {
					this.myAgent.addBehaviour(new SendGameMoveToPlayer(newGame, newGame.getOMarkPlayer()));
				} else if (isCircle) {
					this.myAgent.addBehaviour(new SendGameMoveToPlayer(newGame, newGame.getXMarkPlayer()));
				}
				break;

			case FinalizedRemis: // Send information about result to players +++ end game, delete game in
				this.sendGameResult(gameWrapperNew);
				gameHashMap.remove(newGame.getGameID());
				break;
				
			case FinalizedWon: // Send information about result to players +++ end game, delete game in
				this.sendGameResult(gameWrapperNew);
				gameHashMap.remove(oldGame.getGameID());
				break;
				
			}
			
		} else {
			System.out.println("Wrong Game Move");
		}
		
	}
	
	/**
	 * Send the game result to all player.
	 * @param gameWrapper the game wrapper
	 */
	private void sendGameResult(GameWrapper gameWrapper) {
		
		AbstractPlayer winner = gameWrapper.getWinner();
		AbstractPlayer loser  = gameWrapper.getLoser();
		
		if (winner==null && loser==null) {
			// --- Create remis instance ---------------------------- 
			GameRemis gameRemis = new GameRemis();
			gameRemis.setGame(gameWrapper.getGame());
			
			this.sendGameResult(gameWrapper.getGame().getXMarkPlayer().getAid(), gameRemis);
			this.sendGameResult(gameWrapper.getGame().getOMarkPlayer().getAid(), gameRemis);
			this.gameMasterAgent.printToUiConsole("Remis in game between " + gameWrapper.getGame().getXMarkPlayer().getAid().getName() + " (X) and " + gameWrapper.getGame().getOMarkPlayer().getAid().getName() + " (O)", false);
			
		} else {
			// --- Create winner loser instance ---------------------
			GameWon gameWon = new GameWon();
			gameWon.setGame(gameWrapper.getGame());
			this.sendGameResult(winner.getAid(), gameWon);
			
			GameLost gameLost = new GameLost();
			gameLost.setGame(gameWrapper.getGame());
			this.sendGameResult(loser.getAid(), gameLost);
			winner.setScore(winner.getScore()+1);
			
			List<AbstractPlayer> agentList = this.gameMasterAgent.getGameMasterBoardModel().getListPlayingAgents();
			AbstractPlayer oldAbstractPlayer;
			if (agentList != null) {
				for (int i = 0; i < agentList.size(); i++) {
					oldAbstractPlayer = this.gameMasterAgent.getGameMasterBoardModel().getListPlayingAgents().get(i);
					if (oldAbstractPlayer.getAid().equals(winner.getAid())){
						this.gameMasterAgent.getGameMasterBoardModel().getListPlayingAgents().get(i).setScore(oldAbstractPlayer.getScore()+1);
					}
				}
						
			} 
			
			this.gameMasterAgent.printToUiConsole("Game was won by " + winner.getAid().getName() + " (against  " + loser.getAid().getName() + ")", false);
			
			
		}
		this.gameMasterAgent.updateUI();
	}
	
	/**
	 * Sends the specified game result to the agent.
	 *
	 * @param receiver the receiver
	 * @param gameResult the game result
	 */
	private void sendGameResult(AID receiver, GameResult gameResult) {
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM); 
		msg.addReceiver(receiver);
		msg.setLanguage(new SLCodec().getName());
		msg.setOntology(TicTacToeOntology.getInstance().getName());
		
		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());	
		agentAction.setAction(gameResult);
		
		try {
			this.myAgent.getContentManager().fillContent(msg, agentAction);
			this.myAgent.send(msg);
			
		} catch (CodecException | OntologyException e) {
			System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
		}
	}
	
}
