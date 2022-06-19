package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameMove
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class GameMove implements Concept {

   /**
* Protege name: markType
   */
   private AbstractMarkType markType;
   public void setMarkType(AbstractMarkType value) { 
    this.markType=value;
   }
   public AbstractMarkType getMarkType() {
     return this.markType;
   }

   /**
* Protege name: gameColumn
   */
   private int gameColumn;
   public void setGameColumn(int value) { 
    this.gameColumn=value;
   }
   public int getGameColumn() {
     return this.gameColumn;
   }

   /**
* Protege name: gameID
   */
   private int gameID;
   public void setGameID(int value) { 
    this.gameID=value;
   }
   public int getGameID() {
     return this.gameID;
   }

   /**
* Protege name: gameRow
   */
   private int gameRow;
   public void setGameRow(int value) { 
    this.gameRow=value;
   }
   public int getGameRow() {
     return this.gameRow;
   }

}
