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
	private AID senderAID;
	
	/**
	 * Instantiates a new registration receive behaviour.
	 *
	 * @param gameMasterAgent the game master agent
	 * @param register the register
	 * @param senderAID the senderAID
	 */
	public RegistrationReceiveBehaviour(GameMasterAgent gameMasterAgent, Register register, AID senderAID) {
		super(gameMasterAgent);
		this.gameMasterAgent = gameMasterAgent;
		this.register = register;
		this.senderAID = senderAID;
	}
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		
		// ---  Get new player and the GameMasterBoardModel -----------------------------------
		AbstractPlayer newPlayer = this.register.getPlayer();
		GameMasterBoardModel gmBoard = this.gameMasterAgent.getGameMasterBoardModel();

		// --- Register new player, if not already registered in the GameMasters blackboard ---
		gmBoard.addPlayingAgent(this.register.getPlayer());
		
		//Check if there is an open/pending game where a second player can join	
		Game gamePending = gmBoard.getGamePending(); 
		if (gamePending==null && newPlayer!=null) {
			// --- Create game and place as pending ---------------------------
			Game game = GameWrapper.createGame(newPlayer, null, true);
			gmBoard.setGamePending(game);
			this.gameMasterAgent.printToUiConsole("New game with player " + senderAID.getName() + " is waiting for second player ", false);
			
		} else if (newPlayer!=null){
			
			if (gamePending.getOMarkPlayer()==null) {
				gamePending.setOMarkPlayer(newPlayer);
			} else if (gamePending.getXMarkPlayer() == null) {
				gamePending.setXMarkPlayer(newPlayer);
			}			
			gmBoard.getGameHashMap().put(gamePending.getGameID(), gamePending);
			gmBoard.setGamePending(null);

			//Send Board to first Player which is always the player with mark O
			this.myAgent.addBehaviour(new SendGameMoveToPlayer(gamePending, gamePending.getXMarkPlayer()));
			this.gameMasterAgent.printToUiConsole("Starting game " + gamePending.getXMarkPlayer().getAid().getName() + " (X) vs. " + gamePending.getOMarkPlayer().getAid().getName() + " (O)", false);
			
		}
	}

}
