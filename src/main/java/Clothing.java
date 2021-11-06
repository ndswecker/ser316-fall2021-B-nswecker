/*
  File:     Clothing.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

/**
 * Class: Clothing.
 * Description: Any kind of clothing item can be made.
 */
public class Clothing implements Comparable<Clothing> {
    public double price;
    public String Description;

    // you can assume that the price of $4 per clothing item is correct
    public Clothing() {
        this(4.00, "Generic Offtrack Separate");

    }
    /**
     * Main constructor.
     * @param price is set price.
     * @param descr is a string that describes the clothing item.
     **/
    public Clothing(double price, String descr) {
        this.price = price;
        this.Description = descr;
    }

    // Changed to meet spotbugs recc.
    public int compareTo(Clothing clothes) {
        //if (clothes.price > this.price) {
        if (Double.compare(clothes.price, this.price) > 0) {
            return 1;
        //} else if (clothes.price < this.price) {
        } else if (Double.compare(clothes.price, this.price) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
