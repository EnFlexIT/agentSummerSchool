package org.asSchool.ttt.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JSeparator;

import jade.core.AID;

import javax.swing.JButton;

/**
 * The Class JPanelGameBoard.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class JPanelGameBoard extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7014257835525864282L;

	private static final Color COLOR_BACKGROUND = Color.WHITE;
	private static final Color COLOR_PLAYER_ONE = new Color(0, 0, 205);
	private static final Color COLOR_PLAYER_TWO = new Color(210, 105, 30);
	
	private JPanelPlayer jPanelPlayer1;
	private JPanelPlayer jPanelPlayer2;
	
	private JSeparator separator;
	
	private JPanel jPanelPlayGround;
		private JButton jButton11;
		private JButton jButton12;
		private JButton jButton13;
		private JButton jButton21;
		private JButton jButton22;
		private JButton jButton23;
		private JButton jButton31;
		private JButton jButton32;
		private JButton jButton33;
	
		
		
	/**
	 * Instantiates a new JPanelGameBoard.
	 */
	public JPanelGameBoard() {
		this.initialize();
		this.addActionListener();
	}
	private void initialize() {
		
		this.setBackground(COLOR_BACKGROUND);
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_jPanelPlayer1 = new GridBagConstraints();
		gbc_jPanelPlayer1.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPlayer1.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPlayer1.insets = new Insets(10, 10, 0, 5);
		gbc_jPanelPlayer1.gridx = 0;
		gbc_jPanelPlayer1.gridy = 0;
		add(getJPanelPlayer1(), gbc_jPanelPlayer1);
		GridBagConstraints gbc_jPanelPlayer2 = new GridBagConstraints();
		gbc_jPanelPlayer2.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPlayer2.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPlayer2.insets = new Insets(10, 5, 0, 10);
		gbc_jPanelPlayer2.gridx = 1;
		gbc_jPanelPlayer2.gridy = 0;
		add(getJPanelPlayer2(), gbc_jPanelPlayer2);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 2;
		gbc_separator.insets = new Insets(10, 10, 0, 10);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(getSeparator(), gbc_separator);
		GridBagConstraints gbc_jPanelPlayGround = new GridBagConstraints();
		gbc_jPanelPlayGround.insets = new Insets(10, 10, 10, 10);
		gbc_jPanelPlayGround.gridwidth = 2;
		gbc_jPanelPlayGround.fill = GridBagConstraints.BOTH;
		gbc_jPanelPlayGround.gridx = 0;
		gbc_jPanelPlayGround.gridy = 2;
		add(getJPanelPlayGround(), gbc_jPanelPlayGround);
	}
	 
	private JPanelPlayer getJPanelPlayer1() {
		if (jPanelPlayer1 == null) {
			jPanelPlayer1 = new JPanelPlayer(1, COLOR_PLAYER_ONE);
			jPanelPlayer1.setBackground(COLOR_BACKGROUND);
			jPanelPlayer1.setPreferredSize(new Dimension(200, 45));
		}
		return jPanelPlayer1;
	}
	private JPanelPlayer getJPanelPlayer2() {
		if (jPanelPlayer2 == null) {
			jPanelPlayer2 = new JPanelPlayer(2, COLOR_PLAYER_TWO);
			jPanelPlayer2.setBackground(COLOR_BACKGROUND);
			jPanelPlayer2.setPreferredSize(new Dimension(200, 45));
		}
		return jPanelPlayer2;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	private JPanel getJPanelPlayGround() {
		if (jPanelPlayGround == null) {
			jPanelPlayGround = new JPanel();
			jPanelPlayGround.setBackground(COLOR_BACKGROUND);
			
			GridBagLayout gbl_jPanelPlayGround = new GridBagLayout();
			gbl_jPanelPlayGround.columnWidths = new int[]{0, 0, 0, 0};
			gbl_jPanelPlayGround.rowHeights = new int[]{0, 0, 0, 0};
			gbl_jPanelPlayGround.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_jPanelPlayGround.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			jPanelPlayGround.setLayout(gbl_jPanelPlayGround);
			GridBagConstraints gbc_jButton11 = new GridBagConstraints();
			gbc_jButton11.fill = GridBagConstraints.BOTH;
			gbc_jButton11.insets = new Insets(20, 20, 0, 10);
			gbc_jButton11.gridx = 0;
			gbc_jButton11.gridy = 0;
			jPanelPlayGround.add(getJButton11(), gbc_jButton11);
			GridBagConstraints gbc_jButton12 = new GridBagConstraints();
			gbc_jButton12.fill = GridBagConstraints.BOTH;
			gbc_jButton12.insets = new Insets(20, 10, 0, 10);
			gbc_jButton12.gridx = 1;
			gbc_jButton12.gridy = 0;
			jPanelPlayGround.add(getJButton12(), gbc_jButton12);
			GridBagConstraints gbc_jButton13 = new GridBagConstraints();
			gbc_jButton13.fill = GridBagConstraints.BOTH;
			gbc_jButton13.insets = new Insets(20, 10, 0, 20);
			gbc_jButton13.gridx = 2;
			gbc_jButton13.gridy = 0;
			jPanelPlayGround.add(getJButton13(), gbc_jButton13);
			GridBagConstraints gbc_jButton21 = new GridBagConstraints();
			gbc_jButton21.insets = new Insets(20, 20, 0, 10);
			gbc_jButton21.fill = GridBagConstraints.BOTH;
			gbc_jButton21.gridx = 0;
			gbc_jButton21.gridy = 1;
			jPanelPlayGround.add(getJButton21(), gbc_jButton21);
			GridBagConstraints gbc_jButton22 = new GridBagConstraints();
			gbc_jButton22.insets = new Insets(20, 10, 0, 10);
			gbc_jButton22.fill = GridBagConstraints.BOTH;
			gbc_jButton22.gridx = 1;
			gbc_jButton22.gridy = 1;
			jPanelPlayGround.add(getJButton22(), gbc_jButton22);
			GridBagConstraints gbc_jButton23 = new GridBagConstraints();
			gbc_jButton23.insets = new Insets(20, 10, 0, 20);
			gbc_jButton23.fill = GridBagConstraints.BOTH;
			gbc_jButton23.gridx = 2;
			gbc_jButton23.gridy = 1;
			jPanelPlayGround.add(getJButton23(), gbc_jButton23);
			GridBagConstraints gbc_jButton31 = new GridBagConstraints();
			gbc_jButton31.insets = new Insets(20, 20, 20, 10);
			gbc_jButton31.fill = GridBagConstraints.BOTH;
			gbc_jButton31.gridx = 0;
			gbc_jButton31.gridy = 2;
			jPanelPlayGround.add(getJButton31(), gbc_jButton31);
			GridBagConstraints gbc_jButton32 = new GridBagConstraints();
			gbc_jButton32.insets = new Insets(20, 10, 20, 10);
			gbc_jButton32.fill = GridBagConstraints.BOTH;
			gbc_jButton32.gridx = 1;
			gbc_jButton32.gridy = 2;
			jPanelPlayGround.add(getJButton32(), gbc_jButton32);
			GridBagConstraints gbc_jButton33 = new GridBagConstraints();
			gbc_jButton33.insets = new Insets(20, 10, 20, 20);
			gbc_jButton33.fill = GridBagConstraints.BOTH;
			gbc_jButton33.gridx = 2;
			gbc_jButton33.gridy = 2;
			jPanelPlayGround.add(getJButton33(), gbc_jButton33);
		}
		return jPanelPlayGround;
	}
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton("");
			jButton11.setActionCommand("11");
			jButton11.setFocusable(false);
			jButton11.setPreferredSize(new Dimension(50, 50));
		}
		return jButton11;
	}
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton("");
			jButton12.setActionCommand("12");
			jButton12.setFocusable(false);
			jButton12.setPreferredSize(new Dimension(50, 50));
		}
		return jButton12;
	}
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton("");
			jButton13.setActionCommand("13");
			jButton13.setFocusable(false);
			jButton13.setPreferredSize(new Dimension(50, 50));
		}
		return jButton13;
	}
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton("");
			jButton21.setActionCommand("21");
			jButton21.setFocusable(false);
			jButton21.setPreferredSize(new Dimension(50, 50));
		}
		return jButton21;
	}
	private JButton getJButton22() {
		if (jButton22 == null) {
			jButton22 = new JButton("");
			jButton22.setActionCommand("22");
			jButton22.setFocusable(false);
			jButton22.setPreferredSize(new Dimension(50, 50));
		}
		return jButton22;
	}
	private JButton getJButton23() {
		if (jButton23 == null) {
			jButton23 = new JButton("");
			jButton23.setActionCommand("23");
			jButton23.setFocusable(false);
			jButton23.setPreferredSize(new Dimension(50, 50));
		}
		return jButton23;
	}
	private JButton getJButton31() {
		if (jButton31 == null) {
			jButton31 = new JButton("");
			jButton31.setActionCommand("31");
			jButton31.setFocusable(false);
			jButton31.setPreferredSize(new Dimension(50, 50));
		}
		return jButton31;
	}
	private JButton getJButton32() {
		if (jButton32 == null) {
			jButton32 = new JButton("");
			jButton32.setActionCommand("32");
			jButton32.setFocusable(false);
			jButton32.setPreferredSize(new Dimension(50, 50));
		}
		return jButton32;
	}
	private JButton getJButton33() {
		if (jButton33 == null) {
			jButton33 = new JButton("");
			jButton33.setActionCommand("33");
			jButton33.setFocusable(false);
			jButton33.setPreferredSize(new Dimension(50, 50));
		}
		return jButton33;
	}
	/**
	 * Adds the local action listener to all JButtons.
	 */
	private void addActionListener() {
		
		for (int i = 0; i < this.getJPanelPlayGround().getComponentCount(); i++) {
			Component comp =  this.getJPanelPlayGround().getComponent(i);
			if (comp instanceof JButton) {
				((JButton) comp).addActionListener(this);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() instanceof JButton) {
			JButton jButtonPressed = (JButton) ae.getSource();
			System.out.println("Pressed button " + jButtonPressed.getActionCommand());
		}
	}
	
	/**
	 * Sets the player.
	 *
	 * @param playerAID the player AID
	 * @param playerNo the player no
	 */
	public void setPlayer(AID playerAID, int playerNo) {
		if (playerNo<=1) {
			this.getJPanelPlayer1().setPlayer(playerAID);
		} else {
			this.getJPanelPlayer2().setPlayer(playerAID);
		}
	}
	
	
}
