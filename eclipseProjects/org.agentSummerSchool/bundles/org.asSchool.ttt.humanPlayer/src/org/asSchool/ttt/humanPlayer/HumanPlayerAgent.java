package org.asSchool.ttt.humanPlayer;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameBoard;
import org.asSchool.ttt.dataModel.ontology.GameRow;
import org.asSchool.ttt.dataModel.ontology.HumanPlayer;
import org.asSchool.ttt.ui.GameBoardListener;
import org.asSchool.ttt.ui.JDialogGameBoard;

import agentgui.core.application.Application;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;

/**
 * The Class HumanPlayerAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class HumanPlayerAgent extends AbstractAgentPlayer implements GameBoardListener { 

	private static final long serialVersionUID = -3300871772757135436L;

	private Game gameForDevelopment;
	
	private JDialogGameBoard jDialogGameBoard;
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		
		// --- Check if we're in the development setup --------------
		if (this.isDevelopment()==true) {
			this.getJDialogGameBoard().setGame(this.getGameForDevelopment());
		}
		// --- Show user game board ---------------------------------
		this.getJDialogGameBoard().setVisible(true);
	}
	/**
	 * Checks if is development.
	 * @return true, if is development
	 */
	private boolean isDevelopment() {
		return Application.getProjectFocused().getSimulationSetupCurrent().equals("UI-Development");
	}
	/**
	 * Returns a game instance for development purposes.
	 * @return the game for development
	 */
	private Game getGameForDevelopment() {
		
		HumanPlayer hp1 = new HumanPlayer();
		hp1.setAid(this.getAID());
		hp1.setScore(1);
		
		HumanPlayer hp2 = new HumanPlayer();
		hp2.setAid(new AID("otherPlayer", true));
		hp1.setScore(1);

		return GameWrapper.createGame(hp1, hp2, false);
	}

	/* (non-Javadoc)
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() {
		this.closeGameBoardDialog();
	}
	
	
	/* (non-Javadoc)
	 * @see org.asSchool.ttt.agentPlayer.AbstractAgentPlayer#onMessageReceived(jade.lang.acl.ACLMessage)
	 */
	@Override
	public void onMessageReceived(ACLMessage aclMessage) {
		// --- TODO Act on the received Message ----------- 
		
	}
	
	/* (non-Javadoc)
	 * @see org.asSchool.ttt.ui.GameBoardListener#onGameUpdate(org.asSchool.ttt.dataModel.ontology.Game)
	 */
	@Override
	public void onGameUpdate(Game game) {
		// --- TODO forward new Game instance to other player 
		System.out.println("Got Game instance update");
	}
	
	
	
	/**
	 * Return the JDialogGameBoard.
	 * @return the game board dialog
	 */
	private JDialogGameBoard getJDialogGameBoard() {
		if (jDialogGameBoard==null) {
			jDialogGameBoard = new JDialogGameBoard(this.getAID());
			jDialogGameBoard.setTitle("Human Player board using agent " + this.getLocalName());
			jDialogGameBoard.setGameBoardListener(this);
		}
		return jDialogGameBoard;
	}
	/**
	 * Close game board dialog.
	 */
	private void closeGameBoardDialog() {
		if (jDialogGameBoard!=null) {
			jDialogGameBoard.setVisible(false);
			jDialogGameBoard.dispose();
			jDialogGameBoard = null;
		}
	}
	

}
