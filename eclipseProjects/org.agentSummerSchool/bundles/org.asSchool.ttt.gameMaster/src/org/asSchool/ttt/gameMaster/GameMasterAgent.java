package org.asSchool.ttt.gameMaster;

import org.asSchool.ttt.dataModel.GameMasterBoardModel;
import org.asSchool.ttt.dataModel.ontology.TicTacToeOntology;
import org.asSchool.ttt.gameMaster.behaviour.MessageReceiveBehaviourGameMaster;

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
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		
		this.getContentManager().registerLanguage(new SLCodec());
		this.getContentManager().registerOntology(TicTacToeOntology.getInstance()); 
		
		this.addBehaviour(new MessageReceiveBehaviourGameMaster(this));
		
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
	
	
}
