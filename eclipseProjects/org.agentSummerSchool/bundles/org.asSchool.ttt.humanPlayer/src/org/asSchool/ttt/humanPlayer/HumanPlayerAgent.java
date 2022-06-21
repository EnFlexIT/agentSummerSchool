package org.asSchool.ttt.humanPlayer;

import javax.swing.SwingUtilities;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameAction;
import org.asSchool.ttt.dataModel.ontology.GameLost;
import org.asSchool.ttt.dataModel.ontology.GameRemis;
import org.asSchool.ttt.dataModel.ontology.GameResult;
import org.asSchool.ttt.dataModel.ontology.GameWon;
import org.asSchool.ttt.dataModel.ontology.HumanPlayer;
import org.asSchool.ttt.ui.GameBoardListener;
import org.asSchool.ttt.ui.JDialogGameBoard;

import agentgui.core.application.Application;
import jade.content.AgentAction;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * The Class HumanPlayerAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class HumanPlayerAgent extends AbstractAgentPlayer implements GameBoardListener { 

	private static final long serialVersionUID = -3300871772757135436L;

	private JDialogGameBoard jDialogGameBoard;
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {

		// --- Register codec, ontology and start MessageReceiveBehaviour -----
		super.setup();
		
		// --- Check if we're in the development setup ------------------------
		if (this.isDevelopment()==true) {
			this.setGameToGameBoardDialog(this.getGameForDevelopment());
		}
		// --- Show user game board -------------------------------------------
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HumanPlayerAgent.this.getJDialogGameBoard().setVisible(true);
				HumanPlayerAgent.this.getJDialogGameBoard().validate();
				HumanPlayerAgent.this.getJDialogGameBoard().repaint();
			}
		});
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
	 * @see org.asSchool.ttt.agentPlayer.AbstractAgentPlayer#getPlayer()
	 */
	@Override
	public AbstractPlayer getPlayer() {
		HumanPlayer hpa = new HumanPlayer();
		hpa.setAid(this.getAID());
		return hpa;
	}
	
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() {
		this.closeGameBoardDialog();
	}
	
	/* (non-Javadoc)
	 * @see org.asSchool.ttt.agentPlayer.AbstractAgentPlayer#onMessageReceived(jade.content.Concept)
	 */
	@Override
	public void onMessageReceived(AgentAction agentAction) {

		if (agentAction instanceof Action) {
			// --- Check the action arrived -------------------------
			Action action = (Action) agentAction;
			if (action.getAction() instanceof GameResult) {
				// --- Set game status ------------------------------
				String status = "";
				GameResult gameResult = (GameResult) action.getAction();
				this.setGameToGameBoardDialog(gameResult.getGame());
				if (gameResult instanceof GameWon) {
					status = "You won - Congratulations!";
				} else if (gameResult instanceof GameLost) {
					status = "You lost stupid!";
				} else if (gameResult instanceof GameRemis) {
					status = "Remis - Just try again!";
				}
				this.getJDialogGameBoard().setStatus(status);
				
			} else 	if (action.getAction() instanceof GameAction) {
				// --- Set the new Game to the UI -------------------
				GameAction gameAction = (GameAction) action.getAction();
				this.setGameToGameBoardDialog(gameAction.getGame());
				this.getJDialogGameBoard().setStatus("Your move " + this.getLocalName() + "?");
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.asSchool.ttt.ui.GameBoardListener#onGameUpdate(org.asSchool.ttt.dataModel.ontology.Game)
	 */
	@Override
	public void onGameUpdate(Game game) {
 
		GameAction gameAction = new GameAction();
		gameAction.setGame(game);
		this.sendMessageToGameMaster(ACLMessage.INFORM, gameAction);
		this.getJDialogGameBoard().setStatus("Waiting for response ...");
	}

	
	/**
	 * Sets the game instance to the dialog.
	 * @param game the new game to UI
	 */
	private void setGameToGameBoardDialog(final Game game) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				HumanPlayerAgent.this.getJDialogGameBoard().setGame(game);
			}
		});
	}
	/**
	 * Return the JDialogGameBoard.
	 * @return the game board dialog
	 */
	private JDialogGameBoard getJDialogGameBoard() {
		if (jDialogGameBoard==null) {
			jDialogGameBoard = new JDialogGameBoard(this.getAID(), this.isDevelopment());
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
