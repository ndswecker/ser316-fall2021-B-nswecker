/*
  File:     Casing.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

/**
 * Class: Casing.
 * Description: A required part of the bear that makes the cloth shell.
 */
public class Casing {
    double priceModifier;

    String description;
    /**
     * Default constructor. 
     */
    public Casing() {
        this(1.00, "Default outer shell");
    }
    
    /**
     *  Alternate constructor for specialized casing.
     *  @param price a declared price.
     *  @param descr a description of the casing.
     */
    public Casing(double price, String descr) {
        this.priceModifier = price;
        this.description = descr;
    }
}
