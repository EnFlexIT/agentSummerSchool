package org.asSchool.ttt.dataModel;

import org.asSchool.ttt.dataModel.ontology.Game;

/**
 * The Class GameMasterBoardModel is managed by the game master agent and 
 * contains all active agents and games.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class GameMasterBoardModel {
	
	private Game gamePending;
	private  GameHashMap gameHashMap;
	
	
	public GameHashMap getGameHashMap() {
		if (gameHashMap==null) {	
			gameHashMap = new GameHashMap();
		}
		return gameHashMap;
	}


	public Game getGamePending() {
		return gamePending;
	}
	public void setGamePending(Game gamePending) {
		this.gamePending = gamePending;
	}





}
