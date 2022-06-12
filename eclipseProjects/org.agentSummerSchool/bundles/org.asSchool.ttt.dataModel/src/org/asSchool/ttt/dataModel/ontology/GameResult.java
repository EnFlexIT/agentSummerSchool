package org.asSchool.ttt.dataModel.ontology;

import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameResult
* @author ontology bean generator
* @version 2022/06/13, 01:04:42
*/
public class GameResult extends AbstractGameConcepts{ 

   /**
* Protege name: winner
   */
   private AbstracttPlayer winner;
   public void setWinner(AbstracttPlayer value) { 
    this.winner=value;
   }
   public AbstracttPlayer getWinner() {
     return this.winner;
   }

   /**
* Protege name: loser
   */
   private AbstracttPlayer loser;
   public void setLoser(AbstracttPlayer value) { 
    this.loser=value;
   }
   public AbstracttPlayer getLoser() {
     return this.loser;
   }

}
