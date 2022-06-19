package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: AbstractPlayer
* @author ontology bean generator
* @version 2022/06/19, 20:03:19
*/
public class AbstractPlayer implements Concept {

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

}
