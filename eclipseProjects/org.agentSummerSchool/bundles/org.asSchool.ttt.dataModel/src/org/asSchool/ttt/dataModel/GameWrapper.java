package org.asSchool.ttt.dataModel;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Circle;
import org.asSchool.ttt.dataModel.ontology.Cross;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameBoard;
import org.asSchool.ttt.dataModel.ontology.GameMove;
import org.asSchool.ttt.dataModel.ontology.GameRow;

import jade.core.AID;
import jade.util.leap.ArrayList;
import jade.util.leap.List;

/**
 * The Class GameWrapper serves as helper class to work on a specific {@link Game} instance.
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class GameWrapper {

	public enum GameState {
		InitialState,
		InProgress,
		FinalizedRemis,
		FinalizedWon
	}
	
	public enum GameLineType {
		Row,
		Colum,
		Diagonal
	}
	
	public enum PlayerResult {
		Winner, 
		Looser, 
		Remis
	}
	
	private static final String lineSeparator = System.getProperty("line.separator");
	
	private Game game;

	
	/**
	 * Instantiates a new game wrapper.
	 */
	public GameWrapper() {}
	/**
	 * Instantiates a new game wrapper.
	 * @param game the game
	 */
	public GameWrapper(Game game) {
		this.game = game;
	}

	/**
	 * Sets the game.
	 * @param game the new game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	/**
	 * Gets the game.
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object compObj) {

		if (compObj==null) return false;
		if (!(compObj instanceof GameWrapper)) return false;
		
		GameWrapper gwComp = (GameWrapper) compObj;
		return gwComp.toString().equals(this.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		if (this.getGame()==null) {
			return this.getClass().getSimpleName() + " without Game instance";
		}
		return this.getGame().getXMarkPlayer().getAid().getName() + " <=> " + this.getGame().getOMarkPlayer().getAid().getName();
	}
	

	// ------------------------------------------------------------------------
	// --- Here a static help method that creates a new Game ------------------
	// ------------------------------------------------------------------------

	/**
	 * Creates a new {@link Game} instance with predefined sub instances to avoid exceptions.
	 *
	 * @param player1 the player 1
	 * @param player2 the player 2
	 * @param randomPosition the indicator to randomize the player position
	 * @return the game
	 */
	public static Game createGame(AbstractPlayer player1, AbstractPlayer player2, boolean randomPosition) {
		
		java.util.ArrayList<AbstractPlayer> playerList = new java.util.ArrayList<>();
		playerList.add(player1);
		playerList.add(player2);
		// --- Randomize position? --------------
		if (randomPosition==true) {
			Collections.shuffle(playerList);
		}
		
		// --- Create GameBoard -----------------
		GameBoard gameBoard = new GameBoard();
		gameBoard.setGameRow1(new GameRow());
		gameBoard.setGameRow2(new GameRow());
		gameBoard.setGameRow3(new GameRow());
		
		// --- Randomize an ID ------------------
		int min = 1000000;
		int max = Integer.MAX_VALUE;
		int randomID = ThreadLocalRandom.current().nextInt(min, max);
		
		// --- Create the initial Game ---------- 
		Game game = new Game();
		game.setGameID(randomID);
		game.setXMarkPlayer(playerList.get(0));
		game.setOMarkPlayer(playerList.get(1));
		game.setGameMoveHistory(new ArrayList());
		game.setGameBoard(gameBoard);
		
		return game;
	}
	
	// ------------------------------------------------------------------------
	// --- From here, methods to work with player / agents --------------------
	// ------------------------------------------------------------------------
	/**
	 * Checks if the specified agent is the next mover.
	 * @param parentAgent the parent agent
	 */
	public boolean isNextMover(AID agent) {
		return agent.equals(this.getNextMover());
	}
	/**
	 * Returns the AID of the next mover.
	 * @return the next mover
	 */
	public AID getNextMover() {
		
		if (this.getGame()==null) return null;
		
		AID nextMover = null;
		List gameHistory = this.getGame().getGameMoveHistory();
		if (gameHistory.size()==0) {
			// --- Get the x mark player ------------------
			nextMover = this.getGame().getXMarkPlayer().getAid();
			
		} else {
			// --- Get the last player --------------------
			GameMove gameMoveLast = (GameMove) gameHistory.get(gameHistory.size()-1);
			AbstractMarkType markType = gameMoveLast.getMarkType();
			if (markType instanceof Cross) {
				nextMover = this.getGame().getOMarkPlayer().getAid();
			} else if (markType instanceof Circle) {
				nextMover = this.getGame().getXMarkPlayer().getAid();
			}
			
		}
		return nextMover;
	}
	/**
	 * Return the mark for the specified agent.
	 *
	 * @param agentAID the agents AID
	 * @return the mark to use
	 */
	public AbstractMarkType getMark(AID agentAID) {
		if (this.getGame().getOMarkPlayer().getAid().equals(agentAID)) return new Circle();
		if (this.getGame().getXMarkPlayer().getAid().equals(agentAID)) return new Cross();
		return null;
	}

	
	// ------------------------------------------------------------------------
	// --- From here, methods to work on the GameBoard ------------------------
	// ------------------------------------------------------------------------
	/**
	 * Checks if the GameBoard is free at the specified cell.
	 *
	 * @param row the GameBoard row
	 * @param column the GameBoard column
	 * @return true, if is free cell
	 */
	public boolean isFreeCell(int row, int column) {
		return this.getMark(row, column)==null;
	}
	/**
	 * Return the mark in the GameBoard at the specified postion.
	 *
	 * @param row the GameBoard row
	 * @param column the GameBoard column
	 * @return the mark
	 */
	public AbstractMarkType getMark(int row, int column) {
		GameRow gameRow = this.getGameRow(row);
		return this.getMark(gameRow, column);
	}

	/**
	 * Return the game row for the specified row number.
	 *
	 * @param rowNo the row number
	 * @return the game row
	 */
	public GameRow getGameRow(int rowNo) {
		
		GameRow gameRow = null;
		switch (rowNo) {
		case 1:
			gameRow = this.getGame().getGameBoard().getGameRow1();
			break;
		case 2:
			gameRow = this.getGame().getGameBoard().getGameRow2();
			break;
		case 3:
			gameRow = this.getGame().getGameBoard().getGameRow3();
			break;
		}
		return gameRow;
	}
	/**
	 * Return the mark in the column of the specified GameRow.
	 *
	 * @param gameRow the game row
	 * @param column the column
	 * @return the mark
	 */
	public AbstractMarkType getMark(GameRow gameRow, int column) {
		
		AbstractMarkType mark = null;
		if (gameRow!=null) {
			switch (column) {
			case 1:
				mark = gameRow.getColumn1();
				break;
			case 2:
				mark = gameRow.getColumn2();
				break;
			case 3:
				mark = gameRow.getColumn3();
				break;
			}
		}
		return mark;
	}
	
	
	/**
	 * Sets the specified GameMove into the GameBoard and the game history.
	 *
	 * @param row the GameBoard row
	 * @param column the GameBoard column
	 * @param newMark the new mark
	 */
	public boolean setMark(GameMove gameMove) {
		if (gameMove!=null) {
			return this.setMark(gameMove.getGameRow(), gameMove.getGameColumn(), gameMove.getMarkType());
		}
		return false;
	}
	/**
	 * Sets the specified mark into the GameBoard and the game history.
	 *
	 * @param row the GameBoard row
	 * @param column the GameBoard column
	 * @param newMark the new mark
	 */
	public boolean setMark(int row, int column, AbstractMarkType newMark) {
		
		boolean successful = false;
		if (newMark!=null && this.isFreeCell(row, column)==true) {
			
			GameRow gameRow = this.getGameRow(row);
			if (gameRow!=null) {
				// --- Set mark to game board -------------
				switch (column) {
				case 1:
					gameRow.setColumn1(newMark);
					break;
				case 2:
					gameRow.setColumn2(newMark);
					break;
				case 3:
					gameRow.setColumn3(newMark);
					break;
				}
				// --- Add to game history ----------------
				GameMove gameMove = new GameMove();
				gameMove.setGameRow(row);
				gameMove.setGameColumn(column);
				gameMove.setMarkType(newMark);
				this.getGame().getGameMoveHistory().add(gameMove);
				
			}
			return true;
		}
		return successful;
	}

	// ------------------------------------------------------------------------
	// --- From here, methods to evaluate the currant GameState ---------------
	// ------------------------------------------------------------------------
	/**
	 * Returns the current {@link GameState}.
	 * @return the game state
	 */
	public GameState getGameState() {
		
		// --- In case that the history is empty ... --------------------------
		if (this.getGame().getGameMoveHistory()==null || this.getGame().getGameMoveHistory().size()==0) {
			return GameState.InitialState;
		}

		// --- In case that the game was already won ... ----------------------
		boolean isWon = this.isWon();
		if (isWon==true) {
			return GameState.FinalizedWon;
		}
		
		// --- In case of a remis ... -----------------------------------------
		if (this.getGame().getGameMoveHistory()==null || this.getGame().getGameMoveHistory().size()==9) {
			return GameState.FinalizedRemis;
		}
		
		// --- Otherwise the game is in progress ------------------------------
		return GameState.InProgress;
	}

	/**
	 * Checks if the current game is won.
	 * @return true, if the game is won
	 */
	public boolean isWon() {
		return this.getWinner()!=null;
	}

	/**
	 * Returns the player that won the current game.
	 * @return the winner player
	 */
	public AbstractPlayer getWinner() {
		
		AbstractMarkType winnerMark = this.getWinnerMark();
		if (winnerMark instanceof Cross) {
			return this.getGame().getXMarkPlayer();
		} else if (winnerMark instanceof Circle) {
			return this.getGame().getOMarkPlayer();
		}
		return null;
	}
	
	/**
	 * Returns the player that lost the current game.
	 * @return the winner player
	 */
	public AbstractPlayer getLoser() {
		
		AbstractMarkType winnerMark = this.getWinnerMark();
		if (winnerMark instanceof Circle) {
			return this.getGame().getXMarkPlayer();
		} else if (winnerMark instanceof Cross) {
			return this.getGame().getOMarkPlayer();
		}
		return null;
	}
	
	/**
	 * Return the current winner or <code>null</code> if no winner can be found.
	 * @return the winner mark
	 */
	public AbstractMarkType getWinnerMark() {
		
		AbstractMarkType[][] markArr = transformToMarkArray(this.getGame().getGameBoard());
		
		AbstractMarkType[] gameLine1 = getGameLine(markArr, GameLineType.Row, 0);
		AbstractMarkType winnerMark = getWinnerOfGameLine(gameLine1);
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Row, 1);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Row, 2);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Colum, 0);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Colum, 1);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Colum, 2);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Diagonal, 1);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		if (winnerMark==null) {
			gameLine1 = getGameLine(markArr, GameLineType.Diagonal, -1);
			winnerMark = getWinnerOfGameLine(gameLine1);
		}
		return winnerMark;
	}
	
	// ------------------------------------------------------------------------
	// --- From here, further static help methods -----------------------------
	// ------------------------------------------------------------------------
	/**
	 * Transfers the specified GameBoard into a 2D array of marks, where the first element 
	 * describes row and the second the column.
	 *
	 * @param gb the GameBoard instance to transform
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] transformToMarkArray(GameBoard gb) {
		
		AbstractMarkType[][] markArray = new AbstractMarkType[3][3];
		
		markArray[0][0] = gb.getGameRow1().getColumn1();
		markArray[0][1] = gb.getGameRow1().getColumn2();
		markArray[0][2] = gb.getGameRow1().getColumn3();
		
		markArray[1][0] = gb.getGameRow2().getColumn1();
		markArray[1][1] = gb.getGameRow2().getColumn2();
		markArray[1][2] = gb.getGameRow2().getColumn3();
		
		markArray[2][0] = gb.getGameRow3().getColumn1();
		markArray[2][1] = gb.getGameRow3().getColumn2();
		markArray[2][2] = gb.getGameRow3().getColumn3();
		
		return markArray;
	}
	
	
	// ------------------------------------------------------------------------
	// --- From here, methods to transform, rotate and mirror mark arrays -----
	// ------------------------------------------------------------------------
	/**
	 * Rotates the specified mark array by 180 degrees.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] rotateMarkArrayBy180Degrees(AbstractMarkType[][] originalMarkArray) {
		AbstractMarkType[][] workingMarkArray = rotateMarkArrayBy90DegreesPlus(originalMarkArray);
		return rotateMarkArrayBy90DegreesPlus(workingMarkArray);
	}
	
	/**
	 * Rotates the specified mark array by 90 degrees plus.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] rotateMarkArrayBy90DegreesPlus(AbstractMarkType[][] originalMarkArray) {
		AbstractMarkType[][] transposedMarkArray = transposeMarkArray(originalMarkArray);
		return reverseMarkArrayRows(transposedMarkArray);
	}
	/**
	 * Rotates the specified mark array by 90 degrees minus.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] rotateMarkArrayBy90DegreesMinus(AbstractMarkType[][] originalMarkArray) {
		AbstractMarkType[][] transposedMarkArray = transposeMarkArray(originalMarkArray);
		return reverseMarkArrayColumns(transposedMarkArray);
	}
	
	/**
	 * Transposes the specified mark type array.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the transposed mark type array
	 */
	public static AbstractMarkType[][] transposeMarkArray(AbstractMarkType[][] originalMarkArray) {
		
		AbstractMarkType[][] transposedMarkArray = new AbstractMarkType[3][3];
		
		for (int i = 0; i < originalMarkArray.length; i++) {
			for (int j = i; j < originalMarkArray[0].length; j++) {
				transposedMarkArray[j][i] = originalMarkArray[i][j];
				transposedMarkArray[i][j] = originalMarkArray[j][i];;
			}
		}
		return transposedMarkArray;
	}
	/**
	 * Returns the specified mark array with reversed columns.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] reverseMarkArrayColumns(AbstractMarkType[][] originalMarkArray) {
		
		AbstractMarkType[][] reversedMarkArray = copyMarkArray(originalMarkArray);
		
		for (int i = 0; i < originalMarkArray[0].length; i++) {
            for (int j = 0, k = originalMarkArray[0].length - 1; j < k; j++, k--) {
            	AbstractMarkType temp = originalMarkArray[j][i];
                reversedMarkArray[j][i] = originalMarkArray[k][i];
                reversedMarkArray[k][i] = temp;
            }
		}
		return reversedMarkArray;
	}
	/**
	 * Returns the specified mark array with reversed rows.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] reverseMarkArrayRows(AbstractMarkType[][] originalMarkArray) {
		
		AbstractMarkType[][] reversedMarkArray = copyMarkArray(originalMarkArray);
		
		for (int i = 0; i < originalMarkArray[0].length; i++) {
            for (int j = 0, k = originalMarkArray[0].length - 1; j < k; j++, k--) {
            	AbstractMarkType temp = originalMarkArray[i][j];
            	reversedMarkArray[i][j] = originalMarkArray[i][k];
                reversedMarkArray[i][k] = temp;
            }
		}
		return reversedMarkArray;
	}
	
	/**
	 * Copies the specified mark array.
	 *
	 * @param originalMarkArray the original mark array
	 * @return the abstract mark type[][]
	 */
	public static AbstractMarkType[][] copyMarkArray(AbstractMarkType[][] originalMarkArray) {
		
		AbstractMarkType[][] copiedMarkArray = new AbstractMarkType[3][3];
		for (int row = 0; row < originalMarkArray.length; row++) {
            for (int col=0; col < originalMarkArray[row].length; col++) {
            	copiedMarkArray[row][col] = originalMarkArray[row][col];
            }
		}
		return copiedMarkArray;
	}
	
	/**
	 * Returns a possible game line of the game board as array of marks.
	 *
	 * @param markArray the mark array
	 * @param gameLineType the game line type
	 * @param index the index to use. For a diagonal indexing use a positive value to get the line from bottom left to top right or a negative value to get the line from top left to bottom right 
	 * @return the game line
	 */
	public static AbstractMarkType[] getGameLine(AbstractMarkType[][] markArray, GameLineType gameLineType, int index) {
		
		AbstractMarkType[] gameLineArray = new AbstractMarkType[3];
		
		switch (gameLineType) {
		case Row:
			gameLineArray = markArray[index];
			break;
			
		case Colum:
			gameLineArray[0] = markArray[0][index];
			gameLineArray[1] = markArray[1][index];
			gameLineArray[2] = markArray[2][index];
			break;
			
		case Diagonal:
			if (index>=0) {
				gameLineArray[0] = markArray[2][0];
				gameLineArray[1] = markArray[1][1];
				gameLineArray[2] = markArray[0][2];
						
			} else if (index<0) {
				gameLineArray[0] = markArray[0][0];
				gameLineArray[1] = markArray[1][1];
				gameLineArray[2] = markArray[2][2];
			}
			break;
		}
		return gameLineArray;
	}
	
	/**
	 * Check for the winner mark within a game line. If no winner can be found, the method returns null
	 *
	 * @param gameLineArray the game line array
	 * @return the winner of game line
	 */
	public static AbstractMarkType getWinnerOfGameLine(AbstractMarkType[] gameLineArray) {
		
		// --- Count circles and crosses ------------------
		int countCross  = 0;
		int countCircle = 0;
		for (int i = 0; i < gameLineArray.length; i++) {
			if (gameLineArray[i] instanceof Cross) countCross++;
			if (gameLineArray[i] instanceof Circle) countCircle++;
		}
		
		// --- Check the game line result -----------------
		if (countCross<3 && countCircle<3) return null;
		
		if (countCross==3) return new Cross();
		if (countCircle==3) return new Circle();
		return null;
	}
	
	
	/**
	 * Prints the specified GameBoard to the console.
	 * @param gameBoard the game board
	 */
	public static void print(GameBoard gameBoard) {
		print(transformToMarkArray(gameBoard));
	}
	/**
	 * Prints the specified array of marks.
	 * @param gameBoardArray the game board array
	 */
	public static void print(AbstractMarkType[][] gameBoardArray) {

		for (int i = 0; i < gameBoardArray.length; i++) {
			AbstractMarkType[] row = gameBoardArray[i]; 
			String line = "";
			for (int j = 0; j < row.length; j++) {
				AbstractMarkType mark = row[j];
				if (mark==null) {
					line += " -";
				} else  if (mark instanceof Circle) {
					line += " O";
				} else  if (mark instanceof Cross) {
					line += " X";
				}
			}
			System.out.println(line);
		}
	}

	/**
	 * Returns the specified GameBoard as string.
	 *
	 * @param gameBoard the game board
	 * @return the game board as string
	 */
	public static String getGameBoardAsString(GameBoard gameBoard) {
		return getGameBoardArrayAsString(transformToMarkArray(gameBoard));
	}
	/**
	 * Returns the specified game board array as string.
	 *
	 * @param gameBoardArray the game board array
	 * @return the game board array as string
	 */
	public static String getGameBoardArrayAsString(AbstractMarkType[][] gameBoardArray) {
		
		String gameBoardString = "";
		for (int i = 0; i < gameBoardArray.length; i++) {
			AbstractMarkType[] row = gameBoardArray[i]; 
			String line = "";
			for (int j = 0; j < row.length; j++) {
				AbstractMarkType mark = row[j];
				if (mark==null) {
					line += " -";
				} else  if (mark instanceof Circle) {
					line += " O";
				} else  if (mark instanceof Cross) {
					line += " X";
				}
			}
			gameBoardString += line + lineSeparator;
		}
		return gameBoardString;
	}
	
}
