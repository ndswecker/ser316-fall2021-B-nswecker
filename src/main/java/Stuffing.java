/*
  File:     Stuffing.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

/**
 * Class: Stuffing is of three types and required
 * Description: stuffing is declared with enum
 */
public class Stuffing {
    public enum stuffing {
        BASE, DOWN, FOAM
    }

    stuffing polyStuffing;
    int price;

    public Stuffing(stuffing interiorStuffing) {

        switch (interiorStuffing) {
            case BASE:
                this.polyStuffing = stuffing.BASE;
                this.price = 30;
                break;
            case DOWN:
                this.polyStuffing = stuffing.DOWN;
                this.price = 40;
                break;
            case FOAM:
                this.polyStuffing = stuffing.FOAM;
                this.price = 50;
                break;
        }
    }
}
