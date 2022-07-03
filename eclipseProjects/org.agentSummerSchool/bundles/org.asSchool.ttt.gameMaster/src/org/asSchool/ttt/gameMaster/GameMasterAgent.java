package org.asSchool.ttt.gameMaster;

import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

import org.asSchool.ttt.dataModel.GameMasterBoardModel;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;
import org.asSchool.ttt.gameMaster.behaviour.MessageReceiveBehaviourGameMaster;
import org.asSchool.ttt.ui.gameMaster.JInternalFrameGameMaster;

import agentgui.core.application.Application;
import agentgui.core.project.Project;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;

/**
 * The Class GameMasterAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class GameMasterAgent extends Agent {

	private static final long serialVersionUID = -3300871772757135436L;

	private GameMasterBoardModel gameMasterBoardModel;
	private JInternalFrameGameMaster jInternalFrameGameMaster;
	
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		
		// --- Register Language & Codec ------------------
		this.getContentManager().registerLanguage(new SLCodec());
		this.getContentManager().registerOntology(TicTacToeOntology.getInstance()); 
		
		// --- Open Game Master UI ------------------------
		this.startGameMasterUIinSwingThread();
		
		// --- Start message receive behaviour ------------
		this.addBehaviour(new MessageReceiveBehaviourGameMaster(this));
	}

	/* (non-Javadoc)
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() {
		// --- Close Game Master UI -----------------------
		this.disposeGameMasterUIinSwingThread();
	}
	
	/**
	 * Returns the game masters blackboard model where all running games are managed.
	 * @return the blackboard model
	 */
	public GameMasterBoardModel getGameMasterBoardModel() {
		if (gameMasterBoardModel==null) {
			gameMasterBoardModel = new GameMasterBoardModel();
		}
		return gameMasterBoardModel;
	}

	/**
	 * Gets the game master UI.
	 * @return the game master UI
	 */
	public JInternalFrameGameMaster getGameMasterUI() {
		if (jInternalFrameGameMaster==null) {
			jInternalFrameGameMaster = new JInternalFrameGameMaster(this.getAID(), this.getGameMasterBoardModel());
			jInternalFrameGameMaster.setTitle("GameMaster - Board - " + this.getAID().getLocalName());
			jInternalFrameGameMaster.setClosable(false);
			jInternalFrameGameMaster.setMaximizable(true);
			jInternalFrameGameMaster.setResizable(true);
			jInternalFrameGameMaster.setVisible(true);
		}
		return jInternalFrameGameMaster;
	}
	
	/**
	 * Updates the UI of the game master agent.
	 */
	public void updateUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameMasterAgent.this.getGameMasterUI().updateGameMasterUI();
			}
		});
	}
	/**
	 * Prints the specified message to the UI console.
	 * @param message the message
	 */
	public void printToUiConsole(final String message, final boolean isError) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameMasterAgent.this.getGameMasterUI().printToConsole(message, isError);
			}
		});
	}
	
	/**
	 * Starts the game master UI in the swing thread.
	 */
	private void startGameMasterUIinSwingThread() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameMasterAgent.this.startGameMasterUI();
			}
		});
	}
	/**
	 * Starts game master UI.
	 */
	private void startGameMasterUI() {
		if (Application.isOperatingHeadless()==false) {
			try {
				// --- Get the project desktop ------------
				JDesktopPane desktop = this.getProjectDesktop();
				if (desktop!=null) {
					desktop.add(this.getGameMasterUI());
					// --- Define a movement? -----------------
					int noOfIntFrames = desktop.getAllFrames().length;
					if (noOfIntFrames>1) {
						int movement = (noOfIntFrames-1) * 20;
						this.getGameMasterUI().setLocation(movement, movement);
					}
					this.getGameMasterUI().setMaximum(true);
					this.getGameMasterUI().setSelected(true);
					this.getGameMasterUI().moveToFront();
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Dispose game master UI in the swing thread.
	 */
	private void disposeGameMasterUIinSwingThread() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameMasterAgent.this.disposeGameMasterUI();
			}
		});
	}
	/**
	 * Disposes the game master UI.
	 */
	private void disposeGameMasterUI() {
		JDesktopPane desktop = this.getProjectDesktop();
		if (desktop!=null) {
			desktop.remove(this.getGameMasterUI());
			desktop.validate();
			desktop.repaint();
		}
		this.getGameMasterUI().setVisible(false);
		this.getGameMasterUI().dispose();
		this.jInternalFrameGameMaster = null;
	}
	
	
	/**
	 * Returns the project desktop.
	 * @return the project desktop
	 */
	private JDesktopPane getProjectDesktop() {
		Project project = Application.getProjectFocused(); 
		if (project!=null) {
			return project.getProjectDesktop();
		}
		return null;
	}
	
}
