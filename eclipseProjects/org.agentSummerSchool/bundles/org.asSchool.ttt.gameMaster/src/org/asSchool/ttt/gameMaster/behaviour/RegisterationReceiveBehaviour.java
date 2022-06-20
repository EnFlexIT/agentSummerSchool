package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.gameMaster.*;
import org.asSchool.ttt.dataModel.GameHashMap;
import org.asSchool.ttt.dataModel.GameMasterBoardModel;
import org.asSchool.ttt.dataModel.GameWrapper;
import org.asSchool.ttt.dataModel.ontology.*;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class RegisterationReceiveBehaviour extends OneShotBehaviour {

	private ACLMessage aclMessage;
	
	private GameMasterAgent gameMasterAgent;
	private GameMasterBoardModel gameMasterBoardModel;
	
	private GameHashMap gameHashMap;
	private Cross cross = new Cross();
	private Circle circle = new Circle();
	Register register = new Register();
	AbstractPlayer newPlayer = new AbstractPlayer();
	
	
	public RegisterationReceiveBehaviour(GameMasterAgent myAgent, Register register, AID aid) {
		super(myAgent);
		this.gameMasterAgent = myAgent;
		this.register = register;
		newPlayer.setAid(aid);
		
	}
	//wieso brauchen wir das?
	private GameMasterBoardModel getGameMasterBoardModel() {
		if (gameMasterBoardModel==null) {
			gameMasterBoardModel = this.gameMasterAgent.getGameMasterBoardModel();
		}
		return gameMasterBoardModel;
	}
	
	
	
	@Override
	public void action() {
		
		
		//Get the GameList of the GameMasterAgent
		this.gameHashMap =  this.gameMasterAgent.getGameMasterBoardModel().getGameHashMap(); //get
		
		//Check if there is an open/pending game where a second player can join	
		if (this.getGameMasterBoardModel().getGamePending()==null) {
			
			AgentPlayer ag1 = new AgentPlayer();
			ag1.setAid(null);
			
			Game gameFound = this.getGameMasterBoardModel().getGameHashMap().get(4711);
			this.getGameMasterBoardModel().getGameHashMap().put(4711, gameFound);
			
			Game game = GameWrapper.createGame(ag1, null, true);
			this.getGameMasterBoardModel().setGamePending(game);

			
			sendRegisterAnswer(newPlayer.getMarkType(), register.getPlayer());
//			createNewGame(1, register.getAgentPlayer()); //if there is no game in the list, create a new game with gameID = 1;
		
		} else {
			
			if (this.gameMasterAgent.getGameMasterBoardModel().getGamePending().getOMarkPlayer() == null) {
				this.gameMasterAgent.getGameMasterBoardModel().getGamePending().setOMarkPlayer(newPlayer);
				newPlayer.setMarkType(circle);
			} else if (this.gameMasterAgent.getGameMasterBoardModel().getGamePending().getXMarkPlayer() == null) {
				this.gameMasterAgent.getGameMasterBoardModel().getGamePending().setXMarkPlayer(newPlayer);
				newPlayer.setMarkType(cross);
			}			
			
			this.gameHashMap.put(this.gameMasterAgent.getGameMasterBoardModel().getGamePending().getGameID(), this.gameMasterAgent.getGameMasterBoardModel().getGamePending());
			sendRegisterAnswer(newPlayer.getMarkType(), register.getPlayer());
			
			//Send Board to first Player which is always the player with mark O
			
			SendGameMoveToPlayer sendGameMoveToPlayer = new SendGameMoveToPlayer(this.gameMasterAgent.getGameMasterBoardModel().getGamePending().getGameID(), this.gameMasterAgent.getGameMasterBoardModel().getGamePending().getOMarkPlayer());
			this.myAgent.addBehaviour(sendGameMoveToPlayer);
		}
		
		
	}
	
	private void sendRegisterAnswer(AbstractMarkType mt, AbstractPlayer ap) {
		
		ACLMessage answerMessage = new ACLMessage();
		answerMessage.addReceiver(ap.getAid());
		answerMessage.setLanguage(new SLCodec().getName());
		answerMessage.setOntology(TicTacToeOntology.getInstance().getName());
		
		
		RegisterAnswer registerAnswer = new RegisterAnswer();
		registerAnswer.setMark(mt);
		registerAnswer.setPlayer(ap);
		Action agentAction = new Action();
		agentAction.setActor(this.myAgent.getAID());
		agentAction.setAction(registerAnswer);
		
		// --- Put the offer into the message -------------
		try {
			this.myAgent.getContentManager().fillContent(answerMessage, agentAction);
		} catch (CodecException | OntologyException e) {
			System.err.println(this.myAgent.getLocalName() + " - Error: " + e.getMessage());
		}
		
		this.myAgent.send(answerMessage);
	}

}
