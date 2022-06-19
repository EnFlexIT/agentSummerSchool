package org.asSchool.ttt.dataModel.ontology;

import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameResult
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class GameResult extends AbstractGameConcepts{ 

   /**
* Protege name: remisPlayer1
   */
   private AbstractPlayer remisPlayer1;
   public void setRemisPlayer1(AbstractPlayer value) { 
    this.remisPlayer1=value;
   }
   public AbstractPlayer getRemisPlayer1() {
     return this.remisPlayer1;
   }

   /**
* Protege name: winner
   */
   private AbstractPlayer winner;
   public void setWinner(AbstractPlayer value) { 
    this.winner=value;
   }
   public AbstractPlayer getWinner() {
     return this.winner;
   }

   /**
* Protege name: remisPlayer2
   */
   private AbstractPlayer remisPlayer2;
   public void setRemisPlayer2(AbstractPlayer value) { 
    this.remisPlayer2=value;
   }
   public AbstractPlayer getRemisPlayer2() {
     return this.remisPlayer2;
   }

   /**
* Protege name: loser
   */
   private AbstractPlayer loser;
   public void setLoser(AbstractPlayer value) { 
    this.loser=value;
   }
   public AbstractPlayer getLoser() {
     return this.loser;
   }

}
