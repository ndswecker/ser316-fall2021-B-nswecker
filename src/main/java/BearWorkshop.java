/*
  File:     Main.java
  Author:   SER 316
  Date:     Fall B 2021
  
  Description:
*/

package main.java;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class: BearWorkshop.
 * Description: This class provides functionality for a BearWorkshop class.
 */
public class BearWorkshop implements BearWorkshopInterface {
    // Workshop has a collection of bears
    // Workshop has a customer
    Customer customer;
    List<Bear> bearCart;

    /**
     * Default constructor for the Bear Workshop.
     */
    public BearWorkshop() {
        this("AZ");
    }

    /**
     * This is a parameterized ctor for a BearWorkshop.
     * 
     * @param state customer is in
     */
    public BearWorkshop(String state) {
        bearCart = new LinkedList<>();
        customer = new Customer(state);
    }
    
    /**
     * Getter for the list of bears in the cart
     * */
    public List<Bear> getCart(){
        return this.bearCart;
    }

    /**
     * This is a convenience method to calculate the cost of one bears in the
     * shopping cart for a customer in the BearFactory. It should take the accessory
     * discounts into account correctly.
     * 
     * @param bear to get cost of
     * @return double representation of bear cost TODO: test me and fix me in
     *         assignment 3
     */
    @Override
    public double getCost(Bear bear) { 
        Collections.sort(bear.clothing);
        int numFree = bear.clothing.size() / CLOTHING_DISCOUNT;
        //System.out.println("CLOTHING_DISCOUNT -> " + numFree);
        ArrayList<Clothing> freeClothes = new ArrayList<>();

        for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            if (i < numFree) {
                freeClothes.add(clothes);
            } else {
                bear.price += clothes.price;
                //System.out.println("adding " + clothes.description + " to the price");
            }
        }

        for (NoiseMaker noise : bear.noisemakers) {
            bear.price += noise.price;
        }

        bear.price += bear.stuff.price;
        bear.price += bear.casing.priceModifier;

        if (bear.ink != null) {
            double inkPrice = bear.ink.price;
            //System.out.println("ink price -> " + inkPrice);
            // if bear price is < 70, the ink price is added into the cost
            if (bear.price <= 70) {
                //System.out.println("bear is " + bear.price + " , and so ink cost is added");
                bear.price += bear.ink.price;
            } else {
                System.out.println("bear is > $70, so ink is free");
                System.out.println("price now at -> " + bear.price);
            }
        }

        return bear.price;
    }

    /**
     * Get cost of a bear without any discounts.
     * */
    // Function to get the raw cost of a bear without any discounts
    // TODO: test me and fix me in assignment 3
    public double getRawCost(Bear bear) {
        for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            bear.price += clothes.price;

        }

        for (NoiseMaker noise : bear.noisemakers) {
            bear.price += noise.price;
        }

        if (bear.ink != null) {
            bear.price += bear.ink.price;
        }

        bear.price += bear.stuff.price;
        bear.price += bear.casing.priceModifier;

        double bearPrice = bear.price;
        bear.price = 0;
        System.out.println("\ngetRawCost() return value >>> " + bearPrice);
        bear.price = bearPrice;
        return bearPrice;
    }

    /**
     * Utility method to calculate tax for purchases by customers in different
     * states. You can assume these taxes are what we want, so they are not wrong.
     * 
     * @return
     */
    public double calculateTax() {
        double tax;
        switch (customer.state) {
            case "AZ":
                tax = 1.07;
                break;
            case "NY":
                tax = 1.09;
                break;
            case "VA":
                tax = 1.05;
                break;
            case "DC":
                tax = 1.105;
                break;
            case "CA":
                tax = 1.1;
                break;
            default:
                tax = 1.05;
                break;
        }
        return tax;
    }

    /**
     * Utility method to add a bear to the BearFactory - so ti the shopping cart.
     * 
     * @param bear to add
     * @return true if successful, false otherwise TODO: test me and fix me in
     *         assignment 3
     */
    @Override
    public boolean addBear(Bear bear) {
        if (this.bearCart.add(bear)) {
            return true;
        } else {
            return false;
        }
    }

    // Simple means to remove a bear from the shopping cart
    @Override
    public boolean removeBear(Bear bear) {
        if (this.bearCart.remove(bear)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is a function to allow customers to checkout their shopping cart It
     * should return the total cost of they purchase. TODO: Test me and fix me in
     * assignment 3
     * 
     * @return
     */
    @Override
    public double checkout() {
        if (this.customer.age <= 13) {
            if (this.customer.parent.age < 18) {
                System.out.println("Guardian is too young");
            }
            return -1;
        }
        
        double temp = 0;
        Double cost = Double.valueOf(0.00);
        for (Bear bear : bearCart) {
            cost = cost + getRawCost(bear);
            System.out.println("Getting raw cost, adding " + cost);
        }
        // Calculate 'cost'
        for (Bear bear : this.bearCart) {
            temp += getCost(bear);
            System.out.println("Getting cost, adding " + temp);
        }

        double savings = 0;
        // calculate total cost
        double rawCost = 0;
        // Calculate 'rawCost'
        for (Bear bear : bearCart) {
            System.out.println("pre rawCost loop >>> " + rawCost);
            rawCost += this.getRawCost(bear);
            System.out.println("I have no idea what this number is >>> " + rawCost);
        }

        // calculate adjusted cost
        cost = 0.0;
        for (Bear bear : this.bearCart) {
            cost += this.getCost(bear);
        }
        System.out.print("(" + savings + ")");
        savings += rawCost - cost; // calc delta between raw and prorated cost
        System.out.println("savings += rawCost(" + rawCost + ") - cost(" + cost + ") = (" + savings + ")");

     // DLS_DEAD_LOCAL_STORE: Dead store to local variable IGNORE
        List<Bear> nonFreeBears = new LinkedList<>();
        int counter = 0;
        int numberOfFreeBearsInbearCart = bearCart.size() / 3;
        double discountedCost = 0;
        Bear freeBear = null;

        for (int count = 0; count <= numberOfFreeBearsInbearCart; ++count) {
            for (Bear bear : bearCart) {
                if (freeBear != null && bear.price < freeBear.price) { // SER316 TASK 2 SPOTBUGS FIX (Decided to not fix)
                    freeBear = bear;
                }
                temp += temp - temp * 2 + bear.price;

            }
        }
        cost = temp;

        return cost * calculateTax();
    }

    /**
     * This method returns the savings for advertised bundle savings. Specifically,
     * - Bears are Buy 2 bears, get a third one free. It is always the cheapest bear
     * that is free. The price here is meant when all discounts for a single bear
     * are applied - It is 10% off the cost of a bear when a single bear has 10 or
     * more accessories (anything on a bear is an accessory) that the customer pays
     * for (so if clothes are free these do not count). - Clothes are buy 2, get one
     * free on each bear. Always the cheapest clothes are free. So if a bear has 6
     * clothes, then the two cheapest ones will be free and it would count as 4
     * accessories (see above). - Inking on a specific bear is free if and only if
     * the bear without discounts applied to it costs more than $70. TIP: the
     * implemented savings method in the BearWorkshop1-5 do not use the getCost
     * method implemented in this base class. They implement their own savings
     * calculation All of them do however use the getRawCost method implemented in
     * this base class. EXAMPLE: You buy 3 bears, one bear has 3 clothing items, the
     * other two have 4 clothing items. Non of them have embroidery or noise makers
     * and they have the same stuffing. Now, on each bear one clothing item will be
     * free, since buy 2 get 1 free on a bear. So for costs we have the bear with
     * stuffing. For one we pay only 2 clothing items, for 2 we still pay for 3
     * clothing items. Since all clothing is the same priece the bear with only 2
     * paid clothing items is cheapest. So we will get that bear for free. We will
     * only have to pay for 2 bears, with 3 clothing items each.
     * 
     * @return the savings if the customer would check with the current bears in the
     *         workshop out as double
     */
    public double calculateSavings() {
        // System.out.println("TODO: Implement me in Assignment 3");

        List<Bear> cart = this.bearCart; // Make reference to a bearCart

        double eachBearSavings = 0; // Start a savings for each individual bear at zero
        double allBearsSavings = 0; // Start a savings for all the bears together at zero

        // Iterate thru all bears in the bearCart
        for (int i = 0; i < cart.size(); i++) {
            Bear bear = cart.get(i); // Select each bear
            double rawCost = getRawCost(bear); // Find raw cost of each bear
            double cost = getCost(bear); // Find the cost of each bear
            //System.out.println(rawCost + " <- raw cost of bear " + i);
            //System.out.println(cost + " <- actual cost of bear " + i);
            eachBearSavings += rawCost - cost;
            //System.out.println(eachBearSavings + " <<< each savings at");
        }

        return eachBearSavings;
    }
    
    /** 
     * sortBears method to sort the bearCart from lowest to highest price
     * */
    public void sortBears() {
        List<Bear> cart;
        cart = this.bearCart;
        System.out.println("Unsorted");
        Bear test = null;
        for (int i = 0; i< cart.size(); i++) {
            test = cart.get(i);
            test.price = getRawCost(test);
            System.out.println("Unsorted Price" + test.price);
        }
            
        Collections.sort(cart, new SortByPrice());
        
        System.out.println("Sorted by price");
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(bearCart.get(i).price);
        }
    }
    
    /** 
     * Class: sortByPrice
     * Description: a private class that implements a comparator used to sort bears by price.
     * */
    class SortByPrice implements Comparator<Bear> {
        public int compare(Bear a, Bear b){
            return (int) (a.price - b.price);
        }
    }

}