package org.asSchool.ttt.brainy.behaviour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asSchool.ttt.brainy.BrainyAgentPlayer;
import org.asSchool.ttt.brainy.brain.NormalizedGameBoardArray;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.GameWrapper.GameLineType;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameMove;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The Class DecisionBehaviour.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class DecisionBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	
	private BrainyAgentPlayer playerAgent;
	private GameWrapper gameWrapper;
	private NormalizedGameBoardArray normalizedGameBoardArray;
	
	/**
	 * Instantiates a new gets the game action behaviour.
	 *
	 * @param playerAgent the player agent
	 * @param action the current GameAction that contains the current Game
	 */
	public DecisionBehaviour(BrainyAgentPlayer playerAgent, GameAction action) {
		this.playerAgent = playerAgent;
		this.getGameWrapper().setGame(action.getGame());
	}

	/**
	 * Returns the local {@link GameWrapper}.
	 * @return the game wrapper
	 */
	private GameWrapper getGameWrapper() {
		if (gameWrapper==null) {
			gameWrapper = new GameWrapper();
		}
		return gameWrapper;
	}
	/**
	 * Sets the specified GameMove to the current game.
	 * @param gameMove the new mark
	 */
	private void setMark(GameMove gameMove) {
		
		boolean success = this.getGameWrapper().setMark(gameMove);
		if (success==false) {
			System.err.println("[" + this.getClass().getSimpleName() + "] Error while trying to set mark to GameBoard (" + gameMove.getGameRow() + "|" + gameMove.getGameColumn() + ")!");
		}
	}
	/**
	 * Return the mark (Circle or Cross).
	 * @return the my mark
	 */
	private AbstractMarkType getMyMark() {
		return this.getGameWrapper().getMark(this.playerAgent.getAID());
	}
	/**
	 * Returns the normalized game board array.
	 * @return the normalized game board array
	 */
	private NormalizedGameBoardArray getNormalizedGameBoardArray() {
		if (normalizedGameBoardArray==null) {
			normalizedGameBoardArray = new NormalizedGameBoardArray(this.getGameWrapper().getGame());
		}
		return normalizedGameBoardArray;
	}
	
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		
		// --- Do next GameMove -----------------------------------------------
		this.doGameMove();
		
		// --- Define the next GameAction, to sent to the GameMasgter ---------
		GameAction newGameAction = new GameAction();
		newGameAction.setGame(this.getGameWrapper().getGame());
		
		this.playerAgent.sendMessageToGameMaster(ACLMessage.INFORM, newGameAction);
	}
	
	/**
	 * Does the next game move.
	 */
	private void doGameMove() {

		if (this.isOfensive()==true) {
			// ----------------------------------------------------------------
			// --- Have an offensive strategy ---------------------------------
			// ----------------------------------------------------------------
			if (this.getMoveNumber()==1) {
				// --- First GameMove ---------------------
				this.setMark(this.getOffensiveFirstMove());
				return;
				
			} else if (this.getMoveNumber()==3) {
				// --- Second GameMove --------------------
				this.setMark(this.getOffensiveSecondGameMove());
				return;
				
			} else if (this.getMoveNumber()==5) {
				// --- Third GameMove --------------------
				GameMove gm = this.getWinningOrPreventingGameMove();
				if (gm!=null) {
					this.setMark(gm);
					return;
				} 
				gm = this.getOffensiveThirdGameMove();
				if (gm!=null) {
					this.setMark(gm);
					return;
				}
				
			} else {
				// --- All further game moves -------------
				GameMove gm = this.getWinningOrPreventingGameMove();
				if (gm!=null) {
					this.setMark(gm);
					return;
				} 
			}
			
		} else {
			// ----------------------------------------------------------------
			// --- Have an defensive strategy ---------------------------------
			// ----------------------------------------------------------------
			if (this.getMoveNumber()==2) {
				// --- First GameMove ---------------------
				if (this.getNormalizedGameBoardArray().getNormalizedGameBoardArray()[0][0]!=null) {
					this.setMark(this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark()));
					return;
				} else if (this.getNormalizedGameBoardArray().getNormalizedGameBoardArray()[0][1]!=null) {
					this.setMark(this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark()));
					return;
				} else if (this.getNormalizedGameBoardArray().getNormalizedGameBoardArray()[1][1]!=null) {
					this.setMark(this.getNormalizedGameBoardArray().getInversGameMove(0, 0, this.getMyMark()));
					return;
				} else {
					System.err.println("[" + this.getClass().getSimpleName() + "] First defensive move error: No mark could be found where expected!");
				}
				
			} else {
				// --- All further game moves -------------
				GameMove gm = this.getWinningOrPreventingGameMove();
				if (gm!=null) {
					this.setMark(gm);
					return;
				} 
			}

		}
		
		// --------------------------------------------------------------------
		// --- If no decision was made, just select the next free cell --------
		// --------------------------------------------------------------------
		AbstractMarkType[][] markArray = GameWrapper.transformToMarkArray(this.getGameWrapper().getGame().getGameBoard());
		outerLoop:
		for (int row = 0; row < markArray.length; row++) {
			for (int col = 0; col < markArray[row].length; col++) {
				if (markArray[row][col] == null) {
					// --- Define GameMove ----------------
					GameMove gm = new GameMove();
					gm.setGameRow(row+1);
					gm.setGameColumn(col+1);
					gm.setMarkType(this.getMyMark());
					this.setMark(gm);
					break outerLoop;
				}
			}
		}
		
	}
	
	/**
	 * Returns either the winning or a preventing game move.
	 * @return the winning or preventing game move
	 */
	private GameMove getWinningOrPreventingGameMove() {
		GameMove gm = this.getWinningGameMove();
		if (gm==null) {
			gm = this.getPreventiveGameMoveToAvoidOpponentsWin();
		}
		return gm;
	}
	
	
	/**
	 * Checks if we are in the defensive position.
	 * @return true, if is defensive
	 */
	private boolean isOfensive() {
		return (this.getGameWrapper().getGame().getXMarkPlayer().getAid().equals(this.playerAgent.getAID()));
	}
	/**
	 * Return the current game step.
	 * @return the game step
	 */
	private int getMoveNumber() {
		return this.getGameWrapper().getGame().getGameMoveHistory().size() + 1;
	}
	
	/**
	 * Creates a game move on the specified position with may mark.
	 *
	 * @param column the column number (not index)
	 * @param row the row number (not index)
	 * @return the game move
	 */
	private GameMove createGameMove(int column, int row) {
		GameMove gm = new GameMove();
		gm.setGameColumn(column);
		gm.setGameRow(row);
		gm.setMarkType(this.getMyMark());
		return gm;
	}
	
	/**
	 * Counts the marks in a game row.
	 *
	 * @param gameLine the game line
	 * @return the number of marks in a game row
	 */
	private int countMarks(AbstractMarkType[] gameLine) {
		int markCount = 0;
		for (int i = 0; i < gameLine.length; i++) {
			if (gameLine[i]!=null) markCount++;
		}
		return markCount;
	}
	/**
	 * Counts the own marks in a game row.
	 *
	 * @param gameLine the game line
	 * @return the number of marks in a game row
	 */
	private int countMyMarks(AbstractMarkType[] gameLine) {
		int markCount = 0;
		for (int i = 0; i < gameLine.length; i++) {
			if (gameLine[i]!=null && gameLine[i].getClass().equals(this.getMyMark().getClass())==true) markCount++;
		}
		return markCount;
	}
	/**
	 * Counts the opponents marks in a game row.
	 *
	 * @param gameLine the game line
	 * @return the number of opponents marks in a game row
	 */
	private int countOpponentsMarks(AbstractMarkType[] gameLine) {
		return this.countMarks(gameLine) - this.countMyMarks(gameLine);
	}
	
	// --------------------------------------------------------------
	// --- From here, we have offensive GameMoves ------------------- 
	// --------------------------------------------------------------
	/**
	 * Returns the first offensive move.
	 * @return the first offensive move
	 */
	private GameMove getOffensiveFirstMove() {
		
		List<GameMove> possibleFirstMoves = new ArrayList<>();
		possibleFirstMoves.add(this.createGameMove(1, 1));
		possibleFirstMoves.add(this.createGameMove(1, 3));
		possibleFirstMoves.add(this.createGameMove(3, 1));
		possibleFirstMoves.add(this.createGameMove(3, 3));
		
		Collections.shuffle(possibleFirstMoves);
		return possibleFirstMoves.get(0);
	}
	
	/**
	 * Returns the second offensive game move.
	 * @return the offensive second game move
	 */
	private GameMove getOffensiveSecondGameMove() {
		
		List<GameLineType> freeLineList = new ArrayList<>();
		
		// --- Check normalized game board for free row -------------
		AbstractMarkType[][] nma = this.getNormalizedGameBoardArray().getNormalizedGameBoardArray(); 
		if (this.countMarks(GameWrapper.getGameLine(nma, GameLineType.Row, 0))<=1) freeLineList.add(GameLineType.Row);
		if (this.countMarks(GameWrapper.getGameLine(nma, GameLineType.Colum, 0))<=1) freeLineList.add(GameLineType.Colum);
		if (this.countMarks(GameWrapper.getGameLine(nma, GameLineType.Diagonal, -1))<=1) freeLineList.add(GameLineType.Diagonal);

		// --- Define return type -----------------------------------
		GameMove gameMove2nd = null;
		Collections.shuffle(freeLineList);
		GameLineType freeLnieSelected = freeLineList.get(0);

		switch (freeLnieSelected) {
		case Row:
			gameMove2nd = this.getNormalizedGameBoardArray().getInversGameMove(0, 2, this.getMyMark());
			break;

		case Colum:
			gameMove2nd = this.getNormalizedGameBoardArray().getInversGameMove(2, 0, this.getMyMark());
			break;
			
		case Diagonal:
			gameMove2nd = this.getNormalizedGameBoardArray().getInversGameMove(2, 2, this.getMyMark());
			break;
		}
		return gameMove2nd;
	}
	
	/**
	 * Returns the third offensive game move.
	 * @return the offensive third game move
	 */
	private GameMove getOffensiveThirdGameMove() {
		
		// --- Check normalized game board for free row -------------
		AbstractMarkType[][] nma = this.getNormalizedGameBoardArray().getNormalizedGameBoardArray(); 
		GameWrapper.print(nma);
		
		// --- Find my marks that serve as base for the search ------
		for (int row = 0; row < nma.length; row++) {
			for (int col = 0; col < nma.length; col++) {
				if (nma[row][col]==null) {
					// ----------------------------------------------
					// --- Check if this move has good chances ------
					// ----------------------------------------------					
					AbstractMarkType[][] nmaCopy = GameWrapper.copyMarkArray(nma);
					nmaCopy[row][col] = this.getMyMark();
					if (this.isForkState(nmaCopy)==true) {
						// --- Found a good move --------------------
						return this.getNormalizedGameBoardArray().getInversGameMove(row, col, this.getMyMark());
					}
				}
			}
		} 
		return null;
	}
	
	/**
	 * Checks if the specified game board array provides a fork state that enable to win in the next step.
	 *
	 * @param nmaCopy the normalized mark array to check
	 * @return true, if is for state
	 */
	private boolean isForkState(AbstractMarkType[][] nma) {

		int forkCount = 0;
		
		AbstractMarkType[] gameLine = null;
		
		for (int row = 0; row < nma.length; row++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Row, row);
			if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) forkCount++;
		} 
		
		for (int col = 0; col < nma[0].length; col++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Colum, col);
			if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) forkCount++;
		}
		
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, 1);
		if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) forkCount++;
		
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, -1);
		if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) forkCount++;
		
		return forkCount>=2;
	}
	
	
	/**
	 * Returns the winning game move if it exists.
	 * @return the winning game move
	 */
	private GameMove getWinningGameMove() {
		
		AbstractMarkType[][] nma = this.getNormalizedGameBoardArray().getNormalizedGameBoardArray();
		AbstractMarkType[] gameLine = null;
		
		// --- Check each game line in the rows ---------------------
		for (int row = 0; row < nma.length; row++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Row, row);
			if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) {
				int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
				if (freeGameLineIndex>=0) {
					return this.getNormalizedGameBoardArray().getInversGameMove(row, freeGameLineIndex, this.getMyMark());
				}
			}
		}
		
		// --- Check each game line in the columns ------------------
		for (int col = 0; col < nma[0].length; col++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Colum, col);
			if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) {
				int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
				if (freeGameLineIndex>=0) {
					return this.getNormalizedGameBoardArray().getInversGameMove(freeGameLineIndex, col, this.getMyMark());
				}
			}
		}
		
		// --- Check diagonal game lines -----------------------------
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, 1);
		if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) {
			int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
			switch (freeGameLineIndex) {
			case 0:
				return this.getNormalizedGameBoardArray().getInversGameMove(2, 0, this.getMyMark());
			case 1:
				return this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark());
			case 2:
				return this.getNormalizedGameBoardArray().getInversGameMove(0, 2, this.getMyMark());
			}
		}
		
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, -1);
		if (this.countMarks(gameLine)==2 && this.countMyMarks(gameLine)==2) {
			int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
			switch (freeGameLineIndex) {
			case 0:
				return this.getNormalizedGameBoardArray().getInversGameMove(0, 0, this.getMyMark());
			case 1:
				return this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark());
			case 2:
				return this.getNormalizedGameBoardArray().getInversGameMove(2, 2, this.getMyMark());
			}
		}
		return null;
	}
	
	// --------------------------------------------------------------
	// --- From here, we have defensive GameMoves ------------------- 
	// --------------------------------------------------------------
	
	/**
	 * Returns the preventive game move to avoid that opponent wins.
	 * @return the preventive game move opponents win
	 */
	private GameMove getPreventiveGameMoveToAvoidOpponentsWin() {
		
		AbstractMarkType[][] nma = this.getNormalizedGameBoardArray().getNormalizedGameBoardArray();
		AbstractMarkType[] gameLine = null;
		
		// --- Check each game line in the rows ---------------------
		for (int row = 0; row < nma.length; row++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Row, row);
			if (this.countOpponentsMarks(gameLine)==2) {
				int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
				if (freeGameLineIndex>=0) {
					return this.getNormalizedGameBoardArray().getInversGameMove(row, freeGameLineIndex, this.getMyMark());
				}
			}
		}
		
		// --- Check each game line in the columns ------------------
		for (int col = 0; col < nma[0].length; col++) {
			gameLine = GameWrapper.getGameLine(nma, GameLineType.Colum, col);
			if (this.countOpponentsMarks(gameLine)==2) {
				int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
				if (freeGameLineIndex>=0) {
					return this.getNormalizedGameBoardArray().getInversGameMove(freeGameLineIndex, col, this.getMyMark());
				}
			}
		}
		
		// --- Check diagonal game lines -----------------------------
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, 1);
		if (this.countOpponentsMarks(gameLine)==2) {
			int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
			switch (freeGameLineIndex) {
			case 0:
				return this.getNormalizedGameBoardArray().getInversGameMove(2, 0, this.getMyMark());
			case 1:
				return this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark());
			case 2:
				return this.getNormalizedGameBoardArray().getInversGameMove(0, 2, this.getMyMark());
			}
		}
		
		gameLine = GameWrapper.getGameLine(nma, GameLineType.Diagonal, -1);
		if (this.countOpponentsMarks(gameLine)==2) {
			int freeGameLineIndex = this.getFirstFreeGameLineIndex(gameLine);
			switch (freeGameLineIndex) {
			case 0:
				return this.getNormalizedGameBoardArray().getInversGameMove(0, 0, this.getMyMark());
			case 1:
				return this.getNormalizedGameBoardArray().getInversGameMove(1, 1, this.getMyMark());
			case 2:
				return this.getNormalizedGameBoardArray().getInversGameMove(2, 2, this.getMyMark());
			}
		}
		return null;
	}
	
	/**
	 * Returns the first free game line index.
	 *
	 * @param gameLine the game line
	 * @return the first free game line index
	 */
	private int getFirstFreeGameLineIndex(AbstractMarkType[] gameLine) {
		int freeIndex = -1;
		for (int i = 0; i < gameLine.length; i++) {
			if (gameLine[i]==null) return i;
		}
		return freeIndex;
	}
	
}
