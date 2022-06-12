package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: GameRow
* @author ontology bean generator
* @version 2022/06/13, 01:04:42
*/
public class GameRow implements Concept {

   /**
* Protege name: column2
   */
   private AbstractMarkType column2;
   public void setColumn2(AbstractMarkType value) { 
    this.column2=value;
   }
   public AbstractMarkType getColumn2() {
     return this.column2;
   }

   /**
* Protege name: column3
   */
   private AbstractMarkType column3;
   public void setColumn3(AbstractMarkType value) { 
    this.column3=value;
   }
   public AbstractMarkType getColumn3() {
     return this.column3;
   }

   /**
* Protege name: column1
   */
   private AbstractMarkType column1;
   public void setColumn1(AbstractMarkType value) { 
    this.column1=value;
   }
   public AbstractMarkType getColumn1() {
     return this.column1;
   }

}
