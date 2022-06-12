package org.asSchool.ttt.dataModel.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: AbstracttPlayer
* @author ontology bean generator
* @version 2022/06/13, 01:04:42
*/
public class AbstracttPlayer implements Concept {

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
