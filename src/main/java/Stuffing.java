/*
  File:     Stuffing.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

/**
 * Class: Stuffing is of three types and required.
 * Description: stuffing is declared with enum
 */
public class Stuffing {
    public enum Stuff { // SER316 TASK 2 SPOTBUGS FIX. This should not be changed
        BASE, DOWN, FOAM
    }

    Stuff polyStuffing;
    int price;

    /** 
     * Default constructor.
     * @param interiorStuffing of class stuffing
     * */
    public Stuffing(Stuff interiorStuffing) {

        switch (interiorStuffing) {
            case BASE:
                this.polyStuffing = Stuff.BASE;
                this.price = 30;
                break;
            case DOWN:
                this.polyStuffing = Stuff.DOWN;
                this.price = 40;
                break;
            case FOAM:
                this.polyStuffing = Stuff.FOAM;
                this.price = 50;
                break;
        }
    }
}
