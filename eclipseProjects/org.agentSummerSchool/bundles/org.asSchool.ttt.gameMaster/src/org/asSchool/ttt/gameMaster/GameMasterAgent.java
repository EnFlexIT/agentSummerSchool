package org.asSchool.ttt.gameMaster;

import org.asSchool.ttt.dataModel.BlackboardModel;
import org.asSchool.ttt.gameMaster.behaviour.MessageReceiveBehaviour;

import jade.core.Agent;

/**
 * The Class GameMasterAgent.
 *
 * @author Christian Derksen - SOFTEC - ICB - University of Duisburg-Essen
 */
public class GameMasterAgent extends Agent {

	private static final long serialVersionUID = -3300871772757135436L;

	private BlackboardModel blackboardModel;
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		
		this.addBehaviour(new MessageReceiveBehaviour(this));
		
	}

	/**
	 * Returns the game masters blackboard model where all running games are managed.
	 * @return the blackboard model
	 */
	public BlackboardModel getBlackboardModel() {
		if (blackboardModel==null) {
			blackboardModel = new BlackboardModel();
		}
		return blackboardModel;
	}
	
}
