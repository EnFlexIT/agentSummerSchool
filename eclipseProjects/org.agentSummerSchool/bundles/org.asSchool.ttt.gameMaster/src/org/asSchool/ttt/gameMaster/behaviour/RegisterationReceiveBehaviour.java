package org.asSchool.ttt.gameMaster.behaviour;

import org.asSchool.ttt.gameMaster.*;
import org.asSchool.ttt.dataModel.ontology.*;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class RegisterationReceiveBehaviour extends OneShotBehaviour {

	ACLMessage aclMessage = new ACLMessage();
	GameList gameList = new GameList();
	Cross cross = new Cross();
	Circle circle = new Circle();
	
	
	public RegisterationReceiveBehaviour(Agent myAgent, ACLMessage aclMessage) {
		this.aclMessage = aclMessage;
	}

	@Override
	public void action() {
		
		Register register = new Register();
		//Get the GameList of the GameMasterAgent
		gameList = ((GameMasterAgent) this.myAgent).getGameList();
		Game lastGameInList = new Game();
		lastGameInList = (Game) gameList.getAllGameList().next();
		
		//Check if the last game in the GameList has already two players. if yes, create a new game, if not
		
		if (!gameList.getAllGameList().hasNext()) {
			createNewGame(1, register.getAgentPlayer()); //if there is no game in the list, create a new game with gameID = 1;
		}
		
		else {
			
			while(gameList.getAllGameList().hasNext()) {
			lastGameInList = (Game) gameList.getAllGameList().next(); //overwrites lastGameInList until there is no further Game in GameList
				if (!gameList.getAllGameList().hasNext()) {
					
					if (lastGameInList.getXMarkPlayer() != null) { //if Xplayer (second player) exists, then, create a new game
						
						createNewGame(lastGameInList.getGameID()+1, register.getAgentPlayer());
	
					}
					else { // else, the new player is set as xPlayer
						lastGameInList.setXMarkPlayer(register.getAgentPlayer());
						sendRegisterAnswer(cross, register.getAgentPlayer());
						
						SendGameMoveToPlayer sendGameMoveToPlayer = new SendGameMoveToPlayer(lastGameInList.getGameBoard(), lastGameInList.getGameID(), lastGameInList.getOMarkPlayer());
						this.myAgent.addBehaviour(sendGameMoveToPlayer);
					}
					
				}	
				
			}
		}
		
		
		
	}
	
	private void createNewGame (int gameID, AgentPlayer ap) {
		
		Game newGame = new Game();
		newGame.setGameID(gameID); // GameID is the last gameID increased by one. 
		newGame.setOMarkPlayer(ap); //set new player
		GameBoard gameBoard = new GameBoard();
		gameBoard.setGameRow1(new GameRow());
		gameBoard.setGameRow2(new GameRow());
		gameBoard.setGameRow3(new GameRow());
		newGame.setGameBoard(gameBoard);
		
		gameList.addGameList(newGame);
		
		sendRegisterAnswer(circle, ap); 	//send confirm Message back to Agent
		
	}
	
	private void sendRegisterAnswer(AbstractMarkType mt, AgentPlayer ap) {
		
		ACLMessage answerMessage = new ACLMessage();
		answerMessage.addReceiver(ap.getAid());
		answerMessage.setLanguage(new SLCodec().getName());
		answerMessage.setOntology(TicTacToeOntologyOntology.getInstance().getName());
		
		
		RegisterAnswer registerAnswer = new RegisterAnswer();
		registerAnswer.setMark(mt);
		registerAnswer.setAgentPlayer(ap);
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
