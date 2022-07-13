package org.asSchool.ttt.gameMaster.behaviour;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class TimerBehaviour extends WakerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6520569579624109301L;

	public TimerBehaviour(Agent a, long timeout) {
		super(a, timeout);
		
	}
	
	protected void onWake() {
		this.stop();
	}

}
