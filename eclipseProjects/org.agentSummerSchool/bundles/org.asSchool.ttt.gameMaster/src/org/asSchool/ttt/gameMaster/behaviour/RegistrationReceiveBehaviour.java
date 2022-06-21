package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.dataModel.GameMasterBoardModel;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.Register;
import org.asSchool.ttt.gameMaster.GameMasterAgent;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;


/**
 * The Class RegistrationReceiveBehaviour ...
 *
 * @author Super-Maxi !!!
 */
public class RegistrationReceiveBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 6289802935132485569L;

	private GameMasterAgent gameMasterAgent;
	private Register register;
	
	/**
	 * Instantiates a new registration receive behaviour.
	 *
	 * @param gameMasterAgent the game master agent
	 * @param register the register
	 * @param aid the aid
	 */
	public RegistrationReceiveBehaviour(GameMasterAgent gameMasterAgent, Register register, AID aid) {
		super(gameMasterAgent);
		this.gameMasterAgent = gameMasterAgent;
		this.register = register;
	}
	
	
	
	@Override
	public void action() {
		
		//Get the GameList of the GameMasterAgent
		GameMasterBoardModel gmBoard = this.gameMasterAgent.getGameMasterBoardModel();
		AbstractPlayer newPlayer = this.register.getPlayer();
		
		//Check if there is an open/pending game where a second player can join	
		Game gamePending = gmBoard.getGamePending(); 
		if (gamePending==null) {
			// --- Create game and place as pending ---------------------------
			Game game = GameWrapper.createGame(newPlayer, null, true);
			gmBoard.setGamePending(game);
		
		} else {
			
			if (gamePending.getOMarkPlayer()==null) {
				gamePending.setOMarkPlayer(newPlayer);
			} else if (gamePending.getXMarkPlayer() == null) {
				gamePending.setXMarkPlayer(newPlayer);
			}			
			gmBoard.getGameHashMap().put(gamePending.getGameID(), gamePending);
			gmBoard.setGamePending(null);

			//Send Board to first Player which is always the player with mark O
			this.myAgent.addBehaviour(new SendGameMoveToPlayer(gamePending, gamePending.getXMarkPlayer()));
		}
		
	}

}
