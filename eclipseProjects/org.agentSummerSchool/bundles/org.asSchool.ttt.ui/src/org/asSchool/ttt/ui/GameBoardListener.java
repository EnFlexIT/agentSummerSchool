package org.asSchool.ttt.ui;

import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameBoard;

/**
 * The listener interface for receiving gameBoard events.
 * The class that is interested in processing a gameBoard
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>setGameBoardListener<code> method. When
 * the gameBoard event occurs, that object's appropriate
 * method is invoked.
 */
public interface GameBoardListener {

	/**
	 * Will be invoked, if a selection was done on the {@link GameBoard}.
	 * @param game the new state of the game
	 */
	public void onGameUpdate(Game game);

	/**
	 * Will be invoked, if a human player wants to start a new game.
	 */
	public void startNewGame();
	
}
