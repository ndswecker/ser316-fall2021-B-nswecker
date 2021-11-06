/*
File:   Bear.java
Author: SER 316
Date:   Fall B 2021
  
Description:
*/

package main.java;

import java.util.LinkedList;
import main.java.Stuffing.stuffing;

/**
 * Class: Bear.
 * Description:
 */
public class Bear implements Comparable<Bear> {
    public Casing casing;
    public Stuffing stuff;
    public Embroidery ink;
    public LinkedList<NoiseMaker> noisemakers; // accessory
    public LinkedList<Clothing> clothing; // accessory
    public double price;
    // bear has a shell (requ)
    // bear has stuffing (req)
    // bear has a tattoo/emroider or not (opt)
    // bear has a noisemaker (opt)
/**
 * Default constructor for Bear class.
 */
    public Bear() {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuffing.BASE);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }
/**
 * Start with stuffing constructor for Bear class
 * @param stuff is of class Stuffing 
*/
    public Bear(stuffing stuff) {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuff);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    /**
     * Method: setPrice() Description: To be filled in.
     * 
     * @param incomingPrice
     */
    public void setPrice(double incomingPrice) {
        this.price = incomingPrice;
    }

    /**
     * Method: addNoise() Description: Adds NoiseMaker object to Bear object.
     * @param noise is of class Noise
     */
    public boolean addNoise(NoiseMaker noise) {
        if (this.noisemakers.size() >= 5) {
            return false;
        } else {
            for (NoiseMaker noisemaker : noisemakers) {
                if (noise.spot == noisemaker.spot) {
                    return false;
                }
            }
            noisemakers.add(noise);
            return true;
        }
    }

    /**
     * Method: compareTo() Description: Compares incoming bear price to this bear price.
     * @param bear of class Bear
     */
    @Override
    public int compareTo(Bear bear) {
        return new Double(this.price).compareTo(bear.price);
    }
}