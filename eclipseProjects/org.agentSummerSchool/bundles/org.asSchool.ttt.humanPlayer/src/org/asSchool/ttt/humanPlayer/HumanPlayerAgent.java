package org.asSchool.ttt.humanPlayer;

import org.asSchool.ttt.agentPlayer.AbstractAgentPlayer;
import org.asSchool.ttt.ui.JDialogGameBoard;

import jade.lang.acl.ACLMessage;

/**
 * The Class HumanPlayerAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class HumanPlayerAgent extends AbstractAgentPlayer { 

	private static final long serialVersionUID = -3300871772757135436L;

	private JDialogGameBoard jDialogGameBoard;
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		this.getJDialogGameBoard().setVisible(true);
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
	
	
	
	
	/**
	 * Return the JDialogGameBoard.
	 * @return the j dialog game board
	 */
	private JDialogGameBoard getJDialogGameBoard() {
		if (jDialogGameBoard==null) {
			jDialogGameBoard = new JDialogGameBoard();
			jDialogGameBoard.setTitle("Human Player board using agent " + this.getLocalName());
			jDialogGameBoard.setPlayer(this.getAID(), 0);
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
