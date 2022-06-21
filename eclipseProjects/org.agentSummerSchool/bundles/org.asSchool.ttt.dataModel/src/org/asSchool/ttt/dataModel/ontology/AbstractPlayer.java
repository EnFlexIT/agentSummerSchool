package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: AbstractPlayer
* @author ontology bean generator
* @version 2022/06/21, 19:18:37
*/
public class AbstractPlayer implements Concept {

   /**
* Protege name: score
   */
   private int score;
   public void setScore(int value) { 
    this.score=value;
   }
   public int getScore() {
     return this.score;
   }

   /**
* Protege name: aid
   */
   private AID aid;
   public void setAid(AID value) { 
    this.aid=value;
   }
   public AID getAid() {
     return this.aid;
   }

}
