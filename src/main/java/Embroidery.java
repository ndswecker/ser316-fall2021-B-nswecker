/*
  File:     Embroidery.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

// You can assume the price of $1 per letter is correct

/**
 * Class: Embroidery
 * Description: A string may be "embroidered" on a bear using ink.
 */
public class Embroidery {
    static final double PRICE_PER_LETTER = 1.00; // SER316 TASK 2 SPOTBUGS FIX
    double price;
    String embroidText;
    /** 
     * Only constructor allowed
     * @param embroidery is a String that will be added to the bear. 
     */
    public Embroidery(String embroidery) {
        this.embroidText = embroidery;
        this.price = embroidery.length() * PRICE_PER_LETTER;
    }
}
