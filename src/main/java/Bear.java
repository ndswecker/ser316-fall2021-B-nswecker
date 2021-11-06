/*
File:	Bear.java
Author:	SER 316
Date:	Fall B 2021
  
Description:
*/

package main.java;

import main.java.Stuffing.stuffing;
import java.util.LinkedList;

/**
Class:	Bear

Description:
*/
public class Bear implements Comparable<Bear>{
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


    public Bear() {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuffing.BASE);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    public Bear(stuffing stuff) {
        this.casing = new Casing();
        this.stuff = new Stuffing(stuff);
        noisemakers = new LinkedList<>();
        clothing = new LinkedList<>();
        ink = new Embroidery("");
        price = 0;
    }

    /**
    Method: setPrice()
    Description:
    @param incomingPrice
    @return void
  */
    public void setPrice(double incomingPrice) {
        this.price = incomingPrice;
    }
    
    /**
    Method: addNoise()
    Description: Adds NoiseMaker object to Bear object
    @param noise
    @return boolean
  */
    public boolean addNoise(NoiseMaker noise) {
        if (this.noisemakers.size() >= 5) {
            return false;
        } else {
            for (NoiseMaker noisemaker: noisemakers) {
                if (noise.spot == noisemaker.spot) {
                    return false;
                }
            }
            noisemakers.add(noise);
            return true;
        }
    }

    /**
    Method: compareTo()
    Description: Compares incoming bear price to this bear price
    @param bear
    @return int
  */
    @Override
    public int compareTo(Bear bear) {
        return new Double(this.price).compareTo(bear.price);
    }
}