package org.asSchool.ttt.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.AbstractMarkType;
import org.asSchool.ttt.dataModel.ontology.AbstractPlayer;
import org.asSchool.ttt.dataModel.ontology.Circle;
import org.asSchool.ttt.dataModel.ontology.Cross;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.dataModel.ontology.GameBoard;

import jade.core.AID;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * The Class JPanelGameBoard.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class JPanelGameBoard extends JPanel implements ActionListener {

	private static final long serialVersionUID = 7014257835525864282L;

	private static final Color COLOR_BACKGROUND = Color.WHITE;
	private static final Color COLOR_PLAYER_ONE_X = new Color(210, 105, 30);
	private static final Color COLOR_PLAYER_TWO_O = new Color(0, 0, 205);
	
	private AID parentAgent;
	private boolean isUiDevelopment;
	
	private GameBoardListener gameBoardListener;
	private GameWrapper gameWrapper;

	
	private JPanelPlayer jPanelPlayer1X;
	private JPanelPlayer jPanelPlayer2O;
	
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
	

	private ImageIcon imageIconCircle;
	private int imageSizeCircle;
	private ImageIcon imageIconCircleScaled;

	private ImageIcon imageIconCross;
	private int imageSizeCross;
	private ImageIcon imageIconCrossScaled;
	private JLabel jLableStatus;
	

	/**
	 * Instantiates a new JPanelGameBoard.
	 * 
	 * @param parentAgent the AID of the parent agent
	 */
	public JPanelGameBoard(AID parentAgent) {
		this(parentAgent, false);
	}
	/**
	 * Instantiates a new JPanelGameBoard.
	 *
	 * @param parentAgent the AID of the parent agent
	 * @param isUiDevelopment the is ui development
	 */
	public JPanelGameBoard(AID parentAgent, boolean isUiDevelopment) {
		this.parentAgent = parentAgent;
		this.initialize();
		this.addResizeListener();
		this.addActionListener();
	}
	
	/**
	 * Initializes mthe panel.
	 */
	private void initialize() {
		
		this.setBackground(COLOR_BACKGROUND);
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{50, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_jPanelPlayer1 = new GridBagConstraints();
		gbc_jPanelPlayer1.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPlayer1.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPlayer1.insets = new Insets(10, 10, 0, 5);
		gbc_jPanelPlayer1.gridx = 0;
		gbc_jPanelPlayer1.gridy = 0;
		add(getJPanelPlayer1X(), gbc_jPanelPlayer1);
		
		GridBagConstraints gbc_jPanelPlayer2 = new GridBagConstraints();
		gbc_jPanelPlayer2.fill = GridBagConstraints.HORIZONTAL;
		gbc_jPanelPlayer2.anchor = GridBagConstraints.NORTH;
		gbc_jPanelPlayer2.insets = new Insets(10, 5, 0, 10);
		gbc_jPanelPlayer2.gridx = 1;
		gbc_jPanelPlayer2.gridy = 0;
		add(getJPanelPlayer2O(), gbc_jPanelPlayer2);
		
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 2;
		gbc_separator.insets = new Insets(10, 10, 0, 10);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(getSeparator(), gbc_separator);
		
		GridBagConstraints gbc_jPanelPlayGround = new GridBagConstraints();
		gbc_jPanelPlayGround.insets = new Insets(10, 10, 0, 10);
		gbc_jPanelPlayGround.gridwidth = 2;
		gbc_jPanelPlayGround.fill = GridBagConstraints.BOTH;
		gbc_jPanelPlayGround.gridx = 0;
		gbc_jPanelPlayGround.gridy = 2;
		add(getJPanelPlayGround(), gbc_jPanelPlayGround);
		
		GridBagConstraints gbc_jLableStatus = new GridBagConstraints();
		gbc_jLableStatus.insets = new Insets(0, 10, 20, 10);
		gbc_jLableStatus.gridwidth = 2;
		gbc_jLableStatus.gridx = 0;
		gbc_jLableStatus.gridy = 3;
		add(getJLableStatus(), gbc_jLableStatus);
	}
	 
	private JPanelPlayer getJPanelPlayer1X() {
		if (jPanelPlayer1X == null) {
			jPanelPlayer1X = new JPanelPlayer(1, COLOR_PLAYER_ONE_X);
			jPanelPlayer1X.setBackground(COLOR_BACKGROUND);
			jPanelPlayer1X.setPreferredSize(new Dimension(200, 45));
		}
		return jPanelPlayer1X;
	}
	private JPanelPlayer getJPanelPlayer2O() {
		if (jPanelPlayer2O == null) {
			jPanelPlayer2O = new JPanelPlayer(2, COLOR_PLAYER_TWO_O);
			jPanelPlayer2O.setBackground(COLOR_BACKGROUND);
			jPanelPlayer2O.setPreferredSize(new Dimension(200, 45));
		}
		return jPanelPlayer2O;
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
	
	
	private JLabel getJLableStatus() {
		if (jLableStatus == null) {
			jLableStatus = new JLabel("Status");
			jLableStatus.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLableStatus;
	}
	/**
	 * Sets the status text.
	 * @param statusMessage the new status
	 */
	public void setStatus(String statusMessage) {
		this.getJLableStatus().setText(statusMessage);
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
	/**
	 * Adds the local resize listener.
	 */
	private void addResizeListener() {
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent ce) {
				JPanelGameBoard.this.updateGameBoard();
			}
		});
	}
	
	/**
	 * Sets the game board listener that has to react on changes in the game board (normally an agent).
	 * @param gameBoardListener the new game board listener
	 */
	public void setGameBoardListener(GameBoardListener gameBoardListener) {
		this.gameBoardListener = gameBoardListener;
	}
	
	// ----------------------------------------------------------------------------------	
	// --- From here, methods for image handling ----------------------------------------
	// ----------------------------------------------------------------------------------
	private ImageIcon getImageIconCross() {
		if (imageIconCross==null) {
			imageIconCross = BundleHelper.getImageIcon("Cross.png");
			BufferedImage bi = this.convertToBufferedImage(imageIconCross.getImage());
			this.exchangeColor(bi, Color.WHITE, COLOR_PLAYER_ONE_X);
			imageIconCross = new ImageIcon(bi);
		}
		return imageIconCross;
	}
	private ImageIcon getImageIconCross(int imageSize) {
		if (imageSize<=0) return this.getImageIconCross();
		if (imageSize!=this.imageSizeCross || this.imageIconCrossScaled==null) {
			Image newimg = this.getImageIconCross().getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH);
			this.imageIconCrossScaled = new ImageIcon(newimg);
			this.imageSizeCross = imageSize;
		}
		return this.imageIconCrossScaled;
	}
	private ImageIcon getImageIconCircle() {
		if (imageIconCircle==null) {
			imageIconCircle = BundleHelper.getImageIcon("Circle.png");
			BufferedImage bi = this.convertToBufferedImage(imageIconCircle.getImage());
			this.exchangeColor(bi, Color.WHITE, COLOR_PLAYER_TWO_O);
			imageIconCircle = new ImageIcon(bi);
		}
		return imageIconCircle;
	}
	private ImageIcon getImageIconCircle(int imageSize) {
		if (imageSize<=0) return this.getImageIconCircle();
		if (imageSize!=this.imageSizeCircle || this.imageIconCircleScaled==null) {
			Image newimg = this.getImageIconCircle().getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH);
			this.imageIconCircleScaled = new ImageIcon(newimg);
			this.imageSizeCircle = imageSize;
		}
		return this.imageIconCircleScaled;
	}
	
	/**
	 * Converts an {@link Image} into a {@link BufferedImage}.
	 * @param image the image to convert
	 * @return the converted buffered image
	 */
	private BufferedImage convertToBufferedImage(Image image) {
		
		// --- Is a buffered image already? -----------------------------------
		if (image instanceof BufferedImage) return (BufferedImage) image;
		
		// --- Create a BufferedImage -----------------------------------------
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return bufferedImage;
	}
	/**
	 * Replaces a specified color with another one in the specified image instance.
	 * @param image    The image
	 * @param oldColor The color that will be replaced
	 * @param newColor The new color
	 * @return The image
	 */
	private void exchangeColor(BufferedImage image, Color oldColor, Color newColor) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				Color currentColor = new Color(image.getRGB(x, y));
				if (currentColor.equals(oldColor)) {
					image.setRGB(x, y, newColor.getRGB());
				}
			}
		}
	}
	
	// ----------------------------------------------------------------------------------	
	// --- From here, methods to apply the Game instance to the visualization -----------
	// ----------------------------------------------------------------------------------
	/**
	 * Returns the local game wrapper.
	 * @return the game wrapper
	 */
	private GameWrapper getGameWrapper() {
		if (gameWrapper==null) {
			gameWrapper = new GameWrapper(null);
		}
		return gameWrapper;
	}
	/**
	 * Sets the game.
	 * @param game the new game
	 */
	public void setGame(Game game) {
		this.getGameWrapper().setGame(game);
		this.updateView();
	}
	/**
	 * Returns the current game.
	 * @return the game
	 */
	public Game getGame() {
		return this.getGameWrapper().getGame();
	}
	
	
	/**
	 * Update view.
	 */
	private void updateView() {
		this.setPlayer(this.getGame().getXMarkPlayer().getAid(), 1);
		this.setPlayer(this.getGame().getOMarkPlayer().getAid(), 2);
		this.updateGameBoard();
	}
	/**
	 * Sets the player.
	 *
	 * @param playerAID the player AID
	 * @param playerNo the player no
	 */
	public void setPlayer(AID playerAID, int playerNo) {
		if (playerNo<=1) {
			this.getJPanelPlayer1X().setPlayer(playerAID);
		} else {
			this.getJPanelPlayer2O().setPlayer(playerAID);
		}
	}
	
	/**
	 * Updates the local game board.
	 */
	private void updateGameBoard() {
		if (this.getGame()!=null && this.getGame().getGameBoard()!=null) {
			this.setGameBoard(this.getGame().getGameBoard());
		}
	}
	/**
	 * Sets the game board.
	 * @param gameBoard the new game board
	 */
	private void setGameBoard(GameBoard gameBoard) {
		
		this.setGameBoard("11", gameBoard.getGameRow1().getColumn1());
		this.setGameBoard("12", gameBoard.getGameRow1().getColumn2());
		this.setGameBoard("13", gameBoard.getGameRow1().getColumn3());
		
		this.setGameBoard("21", gameBoard.getGameRow2().getColumn1());
		this.setGameBoard("22", gameBoard.getGameRow2().getColumn2());
		this.setGameBoard("23", gameBoard.getGameRow2().getColumn3());
		
		this.setGameBoard("31", gameBoard.getGameRow3().getColumn1());
		this.setGameBoard("32", gameBoard.getGameRow3().getColumn2());
		this.setGameBoard("33", gameBoard.getGameRow3().getColumn3());
	}
	/**
	 * Sets the specified game field.
	 *
	 * @param actionCommand the action command
	 * @param markType the mark type
	 */
	private void setGameBoard(String actionCommand, AbstractMarkType markType) {
		
		JButton jButton = this.getJButtonByActionCommand(actionCommand);
		if (jButton!=null) {
			// --- Get the required image size --------------------------------
			int imageSize = Math.min(jButton.getWidth(), jButton.getHeight()) - 10;
			// --- Set the specified mark to the gameField button -------------
			if (markType instanceof Circle) {
				// --- Set a circle -------------------------------------------
				jButton.setIcon(this.getImageIconCircle(imageSize));
				
			} else if (markType instanceof Cross) {
				// --- Set a cross --------------------------------------------
				jButton.setIcon(this.getImageIconCross(imageSize));
				
			} else {
				// --- Set an empty image -------------------------------------
				jButton.setIcon(null);
			}
		}
	}
	/**
	 * Return the JButton that uses the specified action command.
	 *
	 * @param actionCommand the action command
	 * @return the j button by action command
	 */
	private JButton getJButtonByActionCommand(String actionCommand) {
		
		for (int i = 0; i < this.getJPanelPlayGround().getComponentCount(); i++) {
			Component comp =  this.getJPanelPlayGround().getComponent(i);
			if (comp instanceof JButton) {
				JButton jButtonCheck = ((JButton) comp);
				if (jButtonCheck.getActionCommand().equals(actionCommand)==true) {
					return jButtonCheck;
				}
			}
		}
		return null;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() instanceof JButton) {
			// --- Which button was pressed? ----------------------------------
			JButton jButtonPressed = (JButton) ae.getSource();
			int[] sel = this.getGameBoardSelection(jButtonPressed.getActionCommand());
			
			// --- Who I am? ---------------------------------------------------
			AID nextMover = this.parentAgent;
			if (this.isUiDevelopment==true && this.getGameWrapper().isNextMover(nextMover)==false) {
				// -------------------------------------------------------------
				// --- Only for UI development purposes !!! --------------------
				// -------------------------------------------------------------
				nextMover = this.getGameWrapper().getNextMover();
			}
			
			// --- Is it allowed to make the next move ? ----------------------
			if (this.getGameWrapper().isNextMover(nextMover)==true) {
				// --- Get my mark and check if the cell is free --------------
				AbstractMarkType myMark = this.getGameWrapper().getMark(nextMover);
				boolean isFreeCell = this.getGameWrapper().isFreeCell(sel[0],sel[1]);
				if (isFreeCell==true) {
					boolean success = this.getGameWrapper().setMark(sel[0], sel[1], myMark);
					if (success==true) {
						// --- Update visualization ---------------------------
						this.updateGameBoard();
						if (this.gameBoardListener!=null) {
							this.gameBoardListener.onGameUpdate(this.getGame());
						}
					}
				}
			}

			// --- Print the resulting GameState ------------------------------
			if (this.isUiDevelopment==true) {
				System.out.println("GameState: " + this.getGameWrapper().getGameState());
				GameWrapper.print(this.getGame().getGameBoard());
				AbstractPlayer winner = this.getGameWrapper().getWinner();
				System.out.println("Winner: " + (winner==null ? "-" : winner.getAid().getLocalName()));
			}
			
		}
	}
	
	/**
	 * Based on the specified action command, this method return the row and column number.
	 *
	 * @param actionCommand the action command
	 * @return the selection as array, where the first element represents the row and the second the column
	 */
	private int[] getGameBoardSelection(String actionCommand) {

		int[] intArray = new int[2];
		String[] selArray = actionCommand.split("");
		for (int i = 0; i < selArray.length; i++) {
			intArray[i] = this.toInteger(selArray[i]);
		}
		return intArray;
	}
	/**
	 * Converts a sting into an int value.
	 *
	 * @param intString the int string
	 * @return the int
	 */
	private int toInteger(String intString) {
		if (intString!=null && intString.isBlank()==false) {
			return Integer.parseInt(intString);
		}
		return -1;
	}
	
}
