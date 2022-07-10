package org.asSchool.ttt.dataModel;

import java.util.ArrayList;
import java.util.List;

import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
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
	private List<AbstractPlayer> listPlayingAgents;
	
	
	/**
	 * Return the HashMap of all running games.
	 * @return the game hash map
	 */
	public GameHashMap getGameHashMap() {
		if (gameHashMap==null) {	
			gameHashMap = new GameHashMap();
		}
		return gameHashMap;
	}

	
	/**
	 * Return the game pending that waits for another player.
	 * @return the game pending
	 */
	public Game getGamePending() {
		return gamePending;
	}
	/**
	 * Sets the game pending that waits for another player.
	 * @param gamePending the new game pending
	 */
	public void setGamePending(Game gamePending) {
		this.gamePending = gamePending;
	}

	
	/**
	 * Returns the list playing agents.
	 * @return the list playing agents
	 */
	public List<AbstractPlayer> getListPlayingAgents() {
		if (listPlayingAgents==null) {
			listPlayingAgents = new ArrayList<>();
		}
		return listPlayingAgents;
	}
	/**
	 * Adds the specified playing agent (if not already in the list of playing agents).
	 * @param player the player
	 */
	public void addPlayingAgent(AbstractPlayer newPlayer) {
		
		if (newPlayer==null) return;
		
		// --- Check to exit because of already registered player ---
		for (AbstractPlayer player : this.getListPlayingAgents()) {
			if (newPlayer.getAid().equals(player.getAid())) {
				return;
			}
			
		}
		// --- New player not found => add to list ------------------
		this.getListPlayingAgents().add(newPlayer);
	}
}
