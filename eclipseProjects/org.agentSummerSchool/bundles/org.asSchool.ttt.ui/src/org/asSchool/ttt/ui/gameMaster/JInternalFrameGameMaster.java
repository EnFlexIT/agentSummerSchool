package org.asSchool.ttt.ui.gameMaster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.asSchool.ttt.dataModel.GameMasterBoardModel;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.GameWrapper.GameState;
import org.asSchool.ttt.dataModel.ontology.Game;
import org.asSchool.ttt.ui.BundleHelper;

import jade.core.AID;

/**
 * The Class JInternalFrameGameMaster is used in the ProjectDesktop to show the
 * current state of all games.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class JInternalFrameGameMaster extends JInternalFrame {

	private static final long serialVersionUID = 7746578541961731624L;
	
	private AID gameMasterAID;
	private GameMasterBoardModel gameMasterBoardModel;
	
	private JPanel jPanelAgentInfo;
	private JLabel jLabelAgentName;
	private JTextField jTextFieldAgentName;
	private JLabel jLabeLabeAgentAddress;
	private JTextField jTextFieldAgentAddress;
	
	private JScrollPane jScrollPaneGameList;
	private DefaultListModel<GameWrapper> gameListModel;
	private JList<GameWrapper> jListGames;
	
	private JTextPane jTextPaneGameState;
	private JLabel jLabelGameState;
	private JLabel jLabelGameHistory;
	private JLabel lblNewLabel;
	private JLabel jLabelConcoleHeader;

	private JScrollPane jScrollPaneAgentConsole;
	private JTextArea jTextAreaAgentConsole;
	private SimpleAttributeSet attBlack;
	private SimpleAttributeSet attRed;
	
	private Integer printLinesCounter = 0;
	private Integer printLinesMax = 400;
	private Integer printLinesCut = 100; 
	private JSplitPane jSplitPaneGameOverview;
	private JPanel jPanelGameList;
	private JPanel jPanelGameInfo;

	
	/**
	 * Instantiates a JInternalFrameGameMaster.
	 *
	 * @param gameMasterAID the game master AID
	 * @param gameMasterBoardModel the game master board model
	 */
	public JInternalFrameGameMaster(AID gameMasterAID, GameMasterBoardModel gameMasterBoardModel) {
		this.gameMasterAID = gameMasterAID;
		this.gameMasterBoardModel = gameMasterBoardModel;
		this.initialize();
		this.updateGameMasterUI();
	}
	private void initialize() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 200, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_jPanelAgentInfo = new GridBagConstraints();
		gbc_jPanelAgentInfo.insets = new Insets(10, 10, 0, 10);
		gbc_jPanelAgentInfo.fill = GridBagConstraints.BOTH;
		gbc_jPanelAgentInfo.gridx = 0;
		gbc_jPanelAgentInfo.gridy = 0;
		this.getContentPane().add(this.getJPanelAgentInfo(), gbc_jPanelAgentInfo);
		
		GridBagConstraints gbc_jSplitPaneGameOverview = new GridBagConstraints();
		gbc_jSplitPaneGameOverview.insets = new Insets(10, 10, 0, 10);
		gbc_jSplitPaneGameOverview.fill = GridBagConstraints.BOTH;
		gbc_jSplitPaneGameOverview.gridx = 0;
		gbc_jSplitPaneGameOverview.gridy = 1;
		this.getContentPane().add(this.getJSplitPaneGameOverview(), gbc_jSplitPaneGameOverview);
		
		GridBagConstraints gbc_jLabelConcoleHeader = new GridBagConstraints();
		gbc_jLabelConcoleHeader.insets = new Insets(10, 10, 0, 10);
		gbc_jLabelConcoleHeader.anchor = GridBagConstraints.WEST;
		gbc_jLabelConcoleHeader.gridx = 0;
		gbc_jLabelConcoleHeader.gridy = 2;
		getContentPane().add(getJLabelConcoleHeader(), gbc_jLabelConcoleHeader);

		GridBagConstraints gbc_jScrollPaneAgentConsole = new GridBagConstraints();
		gbc_jScrollPaneAgentConsole.insets = new Insets(5, 10, 10, 10);
		gbc_jScrollPaneAgentConsole.fill = GridBagConstraints.BOTH;
		gbc_jScrollPaneAgentConsole.gridx = 0;
		gbc_jScrollPaneAgentConsole.gridy = 3;
		this.getContentPane().add(this.getJScrollPaneAgentConsole(), gbc_jScrollPaneAgentConsole);
	}
	
	private JPanel getJPanelAgentInfo() {
		if (jPanelAgentInfo == null) {
			jPanelAgentInfo = new JPanel();
			GridBagLayout gbl_jPanelAgentInfo = new GridBagLayout();
			gbl_jPanelAgentInfo.columnWidths = new int[]{0, 0, 0, 0, 0};
			gbl_jPanelAgentInfo.rowHeights = new int[]{0, 0};
			gbl_jPanelAgentInfo.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_jPanelAgentInfo.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			jPanelAgentInfo.setLayout(gbl_jPanelAgentInfo);
			GridBagConstraints gbc_jLabelAgentName = new GridBagConstraints();
			gbc_jLabelAgentName.anchor = GridBagConstraints.EAST;
			gbc_jLabelAgentName.gridx = 0;
			gbc_jLabelAgentName.gridy = 0;
			jPanelAgentInfo.add(getJLabelAgentName(), gbc_jLabelAgentName);
			GridBagConstraints gbc_jTextFieldAgentName = new GridBagConstraints();
			gbc_jTextFieldAgentName.insets = new Insets(0, 5, 0, 0);
			gbc_jTextFieldAgentName.fill = GridBagConstraints.HORIZONTAL;
			gbc_jTextFieldAgentName.gridx = 1;
			gbc_jTextFieldAgentName.gridy = 0;
			jPanelAgentInfo.add(getJTextFieldAgentName(), gbc_jTextFieldAgentName);
			GridBagConstraints gbc_jLabeLabeAgentAddress = new GridBagConstraints();
			gbc_jLabeLabeAgentAddress.insets = new Insets(0, 10, 0, 0);
			gbc_jLabeLabeAgentAddress.anchor = GridBagConstraints.EAST;
			gbc_jLabeLabeAgentAddress.gridx = 2;
			gbc_jLabeLabeAgentAddress.gridy = 0;
			jPanelAgentInfo.add(getJLabeLabeAgentAddress(), gbc_jLabeLabeAgentAddress);
			GridBagConstraints gbc_jTextFieldAgentAddress = new GridBagConstraints();
			gbc_jTextFieldAgentAddress.insets = new Insets(0, 5, 0, 0);
			gbc_jTextFieldAgentAddress.fill = GridBagConstraints.HORIZONTAL;
			gbc_jTextFieldAgentAddress.gridx = 3;
			gbc_jTextFieldAgentAddress.gridy = 0;
			jPanelAgentInfo.add(getJTextFieldAgentAddress(), gbc_jTextFieldAgentAddress);
		}
		return jPanelAgentInfo;
	}
	private JLabel getJLabelAgentName() {
		if (jLabelAgentName == null) {
			jLabelAgentName = new JLabel("Game-Master Agent:");
			jLabelAgentName.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLabelAgentName;
	}
	private JTextField getJTextFieldAgentName() {
		if (jTextFieldAgentName == null) {
			jTextFieldAgentName = new JTextField();
			jTextFieldAgentName.setPreferredSize(new Dimension(250, 26));
			jTextFieldAgentName.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextFieldAgentName.setEditable(false);
		}
		return jTextFieldAgentName;
	}
	private JLabel getJLabeLabeAgentAddress() {
		if (jLabeLabeAgentAddress == null) {
			jLabeLabeAgentAddress = new JLabel("MTP-Address:");
			jLabeLabeAgentAddress.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLabeLabeAgentAddress;
	}
	private JTextField getJTextFieldAgentAddress() {
		if (jTextFieldAgentAddress == null) {
			jTextFieldAgentAddress = new JTextField();
			jTextFieldAgentAddress.setPreferredSize(new Dimension(250, 26));
			jTextFieldAgentAddress.setFont(new Font("Dialog", Font.PLAIN, 12));
			jTextFieldAgentAddress.setEditable(false);
		}
		return jTextFieldAgentAddress;
	}
	
	/**
	 * Sets the game master AID to the visualization.
	 * @param aid the new game master AID
	 */
	private void setGameMasterAID(AID aid) {
		
		this.getJTextFieldAgentName().setText(aid.getName());
		
		String addresses = "";
		String[] addressArray = aid.getAddressesArray();
		
		for (int i = 0; i < addressArray.length; i++) {
			if (addresses.isBlank()==true) {
				addresses += addressArray[i]; 
			} else {
				addresses += ", " + addressArray[i];//TOID
			}
		}
		this.getJTextFieldAgentAddress().setText(addresses);
	}
	
	
	private JSplitPane getJSplitPaneGameOverview() {
		if (jSplitPaneGameOverview == null) {
			jSplitPaneGameOverview = new JSplitPane();
			jSplitPaneGameOverview.setResizeWeight(0.3);
			jSplitPaneGameOverview.setBorder(BorderFactory.createEmptyBorder());
			jSplitPaneGameOverview.setLeftComponent(getJPanelGameList());
			jSplitPaneGameOverview.setRightComponent(getJPanelGameInfo());
		}
		return jSplitPaneGameOverview;
	}
	private JPanel getJPanelGameList() {
		if (jPanelGameList == null) {
			jPanelGameList = new JPanel();
			
			GridBagLayout gbl_jPanelGameList = new GridBagLayout();
			gbl_jPanelGameList.columnWidths = new int[]{258, 0};
			gbl_jPanelGameList.rowHeights = new int[]{16, 130, 0};
			gbl_jPanelGameList.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_jPanelGameList.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			jPanelGameList.setLayout(gbl_jPanelGameList);
			
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			jPanelGameList.add(this.getLblNewLabel(), gbc_lblNewLabel);
			
			GridBagConstraints gbc_jScrollPaneGameList = new GridBagConstraints();
			gbc_jScrollPaneGameList.insets = new Insets(5, 0, 0, 5);
			gbc_jScrollPaneGameList.fill = GridBagConstraints.BOTH;
			gbc_jScrollPaneGameList.gridx = 0;
			gbc_jScrollPaneGameList.gridy = 1;
			jPanelGameList.add(this.getJScrollPaneGameList(), gbc_jScrollPaneGameList);
		}
		return jPanelGameList;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Active Games");
			lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return lblNewLabel;
	}
	private JScrollPane getJScrollPaneGameList() {
		if (jScrollPaneGameList == null) {
			jScrollPaneGameList = new JScrollPane();
			jScrollPaneGameList.setViewportView(getJListGames());
		}
		return jScrollPaneGameList;
	}
	private JList<GameWrapper> getJListGames() {
		if (jListGames == null) {
			jListGames = new JList<>(this.getListModelGames());
			jListGames.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent lsEv) {
					if (lsEv.getValueIsAdjusting()==false) {
						JInternalFrameGameMaster.this.setGameState();
					}
				}
			});
		}
		return jListGames;
	}
	private DefaultListModel<GameWrapper> getListModelGames() {
		if (gameListModel==null) {
			gameListModel = new DefaultListModel<>();
		}
		return gameListModel;
	}
	private void fillListModelGames() {
		
		// --- Get current selection ----------------------
		GameWrapper gwSelected = this.getJListGames().getSelectedValue();
		
		// --- Clear list model ---------------------------
		this.getListModelGames().clear();

		// --- Get list of games and sort them ------------
		List<Game> gameList = new ArrayList<>(this.gameMasterBoardModel.getGameHashMap().values());
		Collections.sort(gameList, new Comparator<Game>() {
			@Override
			public int compare(Game g1, Game g2) {
				return g1.getXMarkPlayer().getAid().getName().compareTo(g2.getXMarkPlayer().getAid().getName());
			}
		});
		
		// --- Fill list model ----------------------------
		for (Game game : gameList) {
			this.getListModelGames().addElement(new GameWrapper(game));
		}
		
		// --- Re-Select the selected game ----------------
		if (gwSelected!=null) {
			this.getJListGames().setSelectedValue(gwSelected, true);
		}
	}
	
	
	private JPanel getJPanelGameInfo() {
		if (jPanelGameInfo == null) {
			jPanelGameInfo = new JPanel();
			
			GridBagLayout gbl_jPanelGameInfo = new GridBagLayout();
			gbl_jPanelGameInfo.columnWidths = new int[]{0, 0, 0};
			gbl_jPanelGameInfo.rowHeights = new int[]{0, 0, 0};
			gbl_jPanelGameInfo.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_jPanelGameInfo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			jPanelGameInfo.setLayout(gbl_jPanelGameInfo);

			GridBagConstraints gbc_jLabelGameState = new GridBagConstraints();
			gbc_jLabelGameState.insets = new Insets(0, 5, 0, 0);
			gbc_jLabelGameState.anchor = GridBagConstraints.WEST;
			gbc_jLabelGameState.gridx = 0;
			gbc_jLabelGameState.gridy = 0;
			jPanelGameInfo.add(this.getJLabelGameState(), gbc_jLabelGameState);
			
			GridBagConstraints gbc_jLabelGameHistory = new GridBagConstraints();
			gbc_jLabelGameHistory.insets = new Insets(0, 5, 0, 0);
			gbc_jLabelGameHistory.anchor = GridBagConstraints.WEST;
			gbc_jLabelGameHistory.gridx = 1;
			gbc_jLabelGameHistory.gridy = 0;
			jPanelGameInfo.add(this.getJLabelGameHistory(), gbc_jLabelGameHistory);
			
			GridBagConstraints gbc_JTextFieldGameState = new GridBagConstraints();
			gbc_JTextFieldGameState.insets = new Insets(6, 5, 0, 0);
			gbc_JTextFieldGameState.fill = GridBagConstraints.BOTH;
			gbc_JTextFieldGameState.gridx = 0;
			gbc_JTextFieldGameState.gridy = 1;
			jPanelGameInfo.add(this.getJTextFieldGameState(), gbc_JTextFieldGameState);
		}
		return jPanelGameInfo;
	}
	private JLabel getJLabelGameState() {
		if (jLabelGameState == null) {
			jLabelGameState = new JLabel("Game-State");
			jLabelGameState.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLabelGameState;
	}
	private JTextPane getJTextFieldGameState() {
		if (jTextPaneGameState == null) {
			
			StyleContext.NamedStyle textPaneStyle = StyleContext.getDefaultStyleContext().new NamedStyle();
			StyleConstants.setFontFamily(textPaneStyle, "Courier New");
			StyleConstants.setBold(textPaneStyle, true);
			StyleConstants.setFontSize(textPaneStyle, 18);
			StyleConstants.setAlignment(textPaneStyle, StyleConstants.ALIGN_CENTER);
			
			jTextPaneGameState = new JTextPane();
			jTextPaneGameState.setEditable(false);
			jTextPaneGameState.setLogicalStyle(textPaneStyle);
			jTextPaneGameState.setAlignmentX(CENTER_ALIGNMENT);
			jTextPaneGameState.setMargin(new Insets(30, 0, 0, 0));
			
			Dimension stateFieldDim = new Dimension(200, 130);
			jTextPaneGameState.setPreferredSize(stateFieldDim);
			jTextPaneGameState.setMinimumSize(stateFieldDim);
			jTextPaneGameState.setMaximumSize(stateFieldDim);
		}
		return jTextPaneGameState;
	}
	private JLabel getJLabelGameHistory() {
		if (jLabelGameHistory == null) {
			jLabelGameHistory = new JLabel("Game-History");
			jLabelGameHistory.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLabelGameHistory;
	}
	
	
	private JLabel getJLabelConcoleHeader() {
		if (jLabelConcoleHeader == null) {
			jLabelConcoleHeader = new JLabel("Game-Master Console");
			jLabelConcoleHeader.setFont(new Font("Dialog", Font.BOLD, 12));
		}
		return jLabelConcoleHeader;
	}
	private JScrollPane getJScrollPaneAgentConsole() {
		if (jScrollPaneAgentConsole == null) {
			jScrollPaneAgentConsole = new JScrollPane();
			jScrollPaneAgentConsole.setViewportView(getJTextAreaAgentConsole());
		}
		return jScrollPaneAgentConsole;
	}
	private JTextArea getJTextAreaAgentConsole() {
		if (jTextAreaAgentConsole == null) {
			jTextAreaAgentConsole = new JTextArea();
		}
		return jTextAreaAgentConsole;
	}

	/**
	 * Sets the game state to the UI.
	 */
	private void setGameState() {
		
		GameWrapper gwSelected = this.getJListGames().getSelectedValue();
		if (gwSelected!=null) {
			GameState gameState = gwSelected.getGameState();
			String boardString = GameWrapper.getGameBoardAsString(gwSelected.getGame().getGameBoard());
			
			this.getJLabelGameState().setText("Game-State: " + gameState.name());
			this.getJTextFieldGameState().setText(boardString);
			
		} else {
			this.getJLabelGameState().setText("Game-State");
			this.getJTextFieldGameState().setText(null);
		}
	}
	
	/**
	 * Updates the game master UI.
	 */
	public void updateGameMasterUI() {
		
		this.setGameMasterAID(this.gameMasterAID);
		this.fillListModelGames();
		this.setGameState();
	}
	
	
	
	/**
	 * Appends the specified text to the console (as error or as simple message).
	 *
	 * @param text the text
	 * @param isError the boolean indicator for errors 
	 */
	public void printToConsole(String text, boolean isError) {
		
		// --- Work on the incoming text --------
		text = text.trim();
		if (text==null || text.equals("")) {
			return;
		} else if (text.endsWith(BundleHelper.getLineSeparator())==false) {
			text = text + BundleHelper.getLineSeparator();
		}
		// --- Set the right color attribute ----
		AttributeSet attribute = null;
		if (isError == false ) {
	    	attribute = this.getSimpleAttributeSetBlack();
		} else {
			attribute = this.getSimpleAttributeSetRed();
		}		
		
		// --- Insert the text in the document --
		Document consoleDoc = this.getJTextAreaAgentConsole().getDocument();
		try {
			consoleDoc.insertString(consoleDoc.getLength(), text, attribute);
			this.printLinesCounter++;
			
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}

		// --- Set the maximum length of the text shown --- 
		if (printLinesCounter > (this.printLinesMax+printLinesCut) ) {
			// --- Get document as String -------
			String docText = null;
			try {
				docText = consoleDoc.getText(0, consoleDoc.getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			
			// --- Find the lines to remove -----
			int lineSeparatorFound = 0;
			int offset = 0;
			while(lineSeparatorFound < this.printLinesCut ) {
				
				int found = docText.indexOf(BundleHelper.getLineSeparator(), offset);
				if (found == -1) {
					// --- nothing found ---
					break;
				} else {
					lineSeparatorFound++;
					offset = found+1;
				}
				
			}
			// --- Remove the lines -------------
			if (offset>0) {
				offset--;
				offset = offset + BundleHelper.getLineSeparator().length();
				try {
					consoleDoc.remove(0, offset);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				this.printLinesCounter = this.printLinesCounter-lineSeparatorFound;
			}
		}
		
        // --- Focus to the end ---------------------------
        this.getJTextAreaAgentConsole().setCaretPosition(consoleDoc.getLength());
	}
	
	private SimpleAttributeSet getSimpleAttributeSetBlack() {
		if (attBlack==null) {
			attBlack = new SimpleAttributeSet();
			StyleConstants.setFontFamily(attBlack, "Courier");
			StyleConstants.setFontSize(attBlack, 11);
			StyleConstants.setForeground(attBlack, Color.black);
		}
		return attBlack; 
	}
	private SimpleAttributeSet getSimpleAttributeSetRed() {
		if (attRed==null) {
			attRed = new SimpleAttributeSet();
			StyleConstants.setForeground(attRed, Color.red);
			StyleConstants.setFontFamily(attRed, "Courier");
			StyleConstants.setFontSize(attRed, 11);
		}
		return attRed;
	}
	
	
}
