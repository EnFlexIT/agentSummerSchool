package org.asSchool.ttt.ui;

import javax.swing.JPanel;

import jade.core.AID;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * The Class JPanelPlayer.
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class JPanelPlayer extends JPanel {
	
	private static final long serialVersionUID = 3561061011702982501L;
	
	private int playerNumber;
	private Color foregroundColor = Color.BLACK; // As default
	
	private JLabel jLabelPlayer;
	private JLabel jLabelPlayerText;
	private JLabel jLabelPlayerAID;
	private JLabel jLabelPlayerAIDText;

	/**
	 * Instantiates a new JPanelPlayer.
	 *
	 * @param playerNumber the player number in the current game
	 * @param foregroundColor the foreground color
	 */
	public JPanelPlayer(int playerNumber, Color foregroundColor) {
		this.playerNumber = playerNumber;
		if (foregroundColor!=null) {
			this.foregroundColor = foregroundColor;
		}
		this.initialize();
	}
	private void initialize() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 49, 0};
		gridBagLayout.rowHeights = new int[]{16, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_jLabelPlayer = new GridBagConstraints();
		gbc_jLabelPlayer.anchor = GridBagConstraints.WEST;
		gbc_jLabelPlayer.gridx = 0;
		gbc_jLabelPlayer.gridy = 0;
		add(getJLabelPlayer(), gbc_jLabelPlayer);
		
		GridBagConstraints gbc_jLabelPlayerText = new GridBagConstraints();
		gbc_jLabelPlayerText.anchor = GridBagConstraints.WEST;
		gbc_jLabelPlayerText.insets = new Insets(0, 5, 0, 0);
		gbc_jLabelPlayerText.gridx = 1;
		gbc_jLabelPlayerText.gridy = 0;
		add(getJLabelPlayerText(), gbc_jLabelPlayerText);
		
		GridBagConstraints gbc_jLabelPlayerAID = new GridBagConstraints();
		gbc_jLabelPlayerAID.anchor = GridBagConstraints.WEST;
		gbc_jLabelPlayerAID.insets = new Insets(5, 0, 0, 0);
		gbc_jLabelPlayerAID.gridx = 0;
		gbc_jLabelPlayerAID.gridy = 1;
		add(getJLabelPlayerAID(), gbc_jLabelPlayerAID);
		
		GridBagConstraints gbc_jLabelPlayerAIDText = new GridBagConstraints();
		gbc_jLabelPlayerAIDText.insets = new Insets(5, 5, 0, 0);
		gbc_jLabelPlayerAIDText.anchor = GridBagConstraints.WEST;
		gbc_jLabelPlayerAIDText.gridx = 1;
		gbc_jLabelPlayerAIDText.gridy = 1;
		add(getJLabelPlayerAIDText(), gbc_jLabelPlayerAIDText);
		
		this.setPreferredSize(new Dimension(300, 42));
		
	}

	private JLabel getJLabelPlayer() {
		if (jLabelPlayer == null) {
			jLabelPlayer = new JLabel("Player " + this.playerNumber + ":");
			jLabelPlayer.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabelPlayer.setForeground(this.foregroundColor);
		}
		return jLabelPlayer;
	}
	private JLabel getJLabelPlayerText() {
		if (jLabelPlayerText == null) {
			jLabelPlayerText = new JLabel("XXX");
			jLabelPlayerText.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelPlayerText.setForeground(this.foregroundColor);
		}
		return jLabelPlayerText;
	}
	private JLabel getJLabelPlayerAID() {
		if (jLabelPlayerAID == null) {
			jLabelPlayerAID = new JLabel("AID:");
			jLabelPlayerAID.setFont(new Font("Dialog", Font.BOLD, 12));
			jLabelPlayerAID.setForeground(this.foregroundColor);
		}
		return jLabelPlayerAID;
	}
	private JLabel getJLabelPlayerAIDText() {
		if (jLabelPlayerAIDText == null) {
			jLabelPlayerAIDText = new JLabel("aid@host/xxx");
			jLabelPlayerAIDText.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabelPlayerAIDText.setForeground(this.foregroundColor);
		}
		return jLabelPlayerAIDText;
	}
	
	public void setPlayer(AID aidPlayer) {
		if (aidPlayer!=null) {
			this.getJLabelPlayerText().setText(aidPlayer.getLocalName());
			this.getJLabelPlayerAIDText().setText(aidPlayer.getName());
		}
	}
	
}
