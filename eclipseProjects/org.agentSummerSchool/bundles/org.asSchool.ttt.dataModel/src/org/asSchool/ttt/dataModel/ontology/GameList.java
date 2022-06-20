package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameList
* @author ontology bean generator
* @version 2022/06/20, 23:26:38
*/
public class GameList implements Concept {

   /**
* Protege name: gameList
   */
   private List gameList = new ArrayList();
   public void addGameList(AbstractGameConcepts elem) { 
     List oldList = this.gameList;
     gameList.add(elem);
   }
   public boolean removeGameList(AbstractGameConcepts elem) {
     List oldList = this.gameList;
     boolean result = gameList.remove(elem);
     return result;
   }
   public void clearAllGameList() {
     List oldList = this.gameList;
     gameList.clear();
   }
   public Iterator getAllGameList() {return gameList.iterator(); }
   public List getGameList() {return gameList; }
   public void setGameList(List l) {gameList = l; }

}
