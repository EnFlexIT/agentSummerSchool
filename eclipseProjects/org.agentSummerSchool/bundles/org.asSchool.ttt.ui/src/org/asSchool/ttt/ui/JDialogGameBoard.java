package org.asSchool.ttt.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import agentgui.core.config.GlobalInfo;
import jade.core.AID;

public class JDialogGameBoard extends JDialog {

	private static final long serialVersionUID = -1370825022427391067L;
	
	private JPanelGameBoard jPanelGameBoard;


	/**
	 * Instantiates a new j dialog game board.
	 */
	public JDialogGameBoard() {
		this.initialize();
	}
	private void initialize() {
		
		this.setSize(450, 550);
		this.setIconImage(GlobalInfo.getInternalImageAwbIcon16());
		
		this.setModal(false);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.getContentPane().add(getJPanelGameBoard(), BorderLayout.CENTER);
		
		// --- Set Dialog position ----------------------------------
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		int top = (screenSize.height - this.getHeight()) / 2; 
	    int left = (screenSize.width - this.getWidth()) / 2; 
	    this.setLocation(left, top);
		
	}
	private JPanelGameBoard getJPanelGameBoard() {
		if (jPanelGameBoard == null) {
			jPanelGameBoard = new JPanelGameBoard();
		}
		return jPanelGameBoard;
	}
	
	// ------------------------------------------------------------------------
	// --- From here some forward methods to the JPanelGameBoard --------------  
	// ------------------------------------------------------------------------
	
	/**
	 * Sets the specified player.
	 *
	 * @param playerAID the player AID
	 * @param playerNo the player no
	 */
	public void setPlayer(AID playerAID, int playerNo) {
		this.getJPanelGameBoard().setPlayer(playerAID, playerNo);
	}
	
}
