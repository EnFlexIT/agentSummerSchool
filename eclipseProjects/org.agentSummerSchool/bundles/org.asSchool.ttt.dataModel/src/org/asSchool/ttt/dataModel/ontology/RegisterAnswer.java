package org.asSchool.ttt.dataModel.ontology;

import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: RegisterAnswer
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class RegisterAnswer extends RegisterInteraction{ 

   /**
* Protege name: mark
   */
   private AbstractMarkType mark;
   public void setMark(AbstractMarkType value) { 
    this.mark=value;
   }
   public AbstractMarkType getMark() {
     return this.mark;
   }

}
