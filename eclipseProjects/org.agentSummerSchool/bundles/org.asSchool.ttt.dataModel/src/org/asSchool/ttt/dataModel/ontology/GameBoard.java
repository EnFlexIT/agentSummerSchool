package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameBoard
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class GameBoard implements Concept {

   /**
* Protege name: gameRow3
   */
   private GameRow gameRow3;
   public void setGameRow3(GameRow value) { 
    this.gameRow3=value;
   }
   public GameRow getGameRow3() {
     return this.gameRow3;
   }

   /**
* Protege name: gameRow1
   */
   private GameRow gameRow1;
   public void setGameRow1(GameRow value) { 
    this.gameRow1=value;
   }
   public GameRow getGameRow1() {
     return this.gameRow1;
   }

   /**
* Protege name: gameRow2
   */
   private GameRow gameRow2;
   public void setGameRow2(GameRow value) { 
    this.gameRow2=value;
   }
   public GameRow getGameRow2() {
     return this.gameRow2;
   }

}
