/*
  File: Main.java
  Author:	SER 316
  Date:	Fall B 2021
  
  Description:
*/

package main.java;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
Class:	BearWorkshop

Description: This class provides functionality for a BearWorkshop class.
*/
public class BearWorkshop implements BearWorkshopInterface{
    // Workshop has a collection of bears
    // Workshop has a customer
    Customer customer;
    List<Bear> BearCart;

    /**
     * Default constructor for the Bear Workshop
     */
    public BearWorkshop() {
        this("AZ");
    }

    /**
     * This is a parameterized ctor for a BearWorkshop
     * @param state customer is in
     */
    public BearWorkshop(String state) {
        BearCart = new LinkedList<>();
        customer = new Customer(state);
    }
    

    /**
     * This is a convenience method to calculate the cost of one bears in the
     * shopping cart for a customer in the BearFactory. It should take the 
     * accessory discounts into account correctly. 
     * @param bear to get cost of
     * @return double representation of bear cost
     * TODO: test me and fix me in assignment 3
     */
    @Override
    public double getCost(Bear bear) {
        Collections.sort(bear.clothing);
        int numFree = bear.clothing.size() / CLOTHING_DISCOUNT;
        System.out.println("CLOTHING_DISCOUNT -> " + numFree);
        ArrayList<Clothing> freeClothes = new ArrayList<>();
        
    	for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            if (i < numFree) {
                freeClothes.add(clothes);
            } else {
                bear.price += clothes.price;
                System.out.println("adding " + clothes.Description + " to the price");
            }
        }

        for (NoiseMaker noise: bear.noisemakers) {
            bear.price += noise.price;
        }

        bear.price += bear.stuff.price;
        bear.price += bear.casing.priceModifier;
        
        if (bear.ink != null) {
        	double inkPrice = bear.ink.price;
        	System.out.println("ink price -> " + inkPrice);
        	// if bear price is < 70, the ink price is added into the cost
        	if (bear.price <= 70) {
        		System.out.println("bear is " + bear.price + " , and so ink cost is added");
        		bear.price += bear.ink.price;
        	} else {
        		System.out.println("bear is > $70, so ink is free");
        		System.out.println("price now at -> " + bear.price);
        	}
        }

        return bear.price;
    }

    // Function to get the raw cost of a bear without any discounts
   // TODO: test me and fix me in assignment 3
    public double getRawCost(Bear bear) {
        for (int i = 0; i < bear.clothing.size(); i++) {
            Clothing clothes = bear.clothing.get(i);
            bear.price += clothes.price;

        }

        for (NoiseMaker noise: bear.noisemakers) {
            bear.price += noise.price;
        }

        if (bear.ink != null) {
            bear.price += bear.ink.price;
        }

        bear.price += bear.stuff.price;
        bear.price += bear.casing.priceModifier;

        double bearPrice = bear.price;
        bear.price = 0;
        return bearPrice;
    }

    /**
     * Utility method to calculate tax for purchases by customers in different
     * states.
     * You can assume these taxes are what we want, so they are not wrong.
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
     * @param bear to add
     * @return true if successful, false otherwise
     * TODO: test me and fix me in assignment 3
     */
    @Override
    public boolean addBear(Bear bear)       {
        if (this.BearCart.add(bear))        {
            return true;
                                            }
        else                                {
            return false;
                                            }
    }

    // Simple means to remove a bear from the shopping cart
    @Override
    public boolean removeBear(Bear bear)    {
        if (this.BearCart.remove(bear))     {
            return true;
                                            }
        else                                {
            return false;
                                            }
    }

    /**
     * This is a function to allow customers to checkout their shopping cart
     * It should return the total cost of they purchase. 
     * TODO: Test me and fix me in assignment 3
     * @return
     */
    @Override
    public double checkout() {
        if (this.customer.age <= 13) {
            if (this.customer.parent.age < 18)
                System.out.println("Guardian is too young");
                return -1;
        }
        double temp = 0;
        Double Cost = Double.valueOf(0.00);
        for (Bear bear: BearCart) {
            Cost = Cost + getRawCost(bear);
        }
        for (Bear bear: this.BearCart) {
            temp += getCost(bear);
        }


        double savings = 0;
        // calculate total cost
        double rawCost = 0;
        for (Bear bear: BearCart) {
            rawCost += this.getRawCost(bear);
        }

        // calculate adjusted cost
        double cost = 0;
        for (Bear bear: this.BearCart) {
            cost += this.getCost(bear);
        }
        savings += rawCost - cost; // calc delta between raw and prorated cost

        List<Bear> nonFreeBears = new LinkedList<>();
        int counter = 0;
        int numberOfFreeBearsInBearCart = BearCart.size() / 3;
        double discountedCost = 0;
        Bear freeBear = null;

        for (int count = 0; count <= numberOfFreeBearsInBearCart; ++count) {
            for (Bear bear : BearCart) {
                if (freeBear != null && bear.price < freeBear.price)
                    freeBear = bear;
                    temp += temp - temp * 2 + bear.price;

            }
        }
        cost = temp;

        return cost * calculateTax();
    }


    /**
     * This method returns the savings for advertised bundle savings.
     * Specifically, 
     * - Bears are Buy 2 bears, get a third one free. It is always the cheapest bear that is free. The price here is meant when all discounts for a single bear are applied
     * - It is 10% off the cost of a bear when a single bear has 10 or more accessories (anything on a bear is an accessory) that the customer pays for (so if clothes are free these do not count). 
     * - Clothes are buy 2, get one free on each bear. Always the cheapest clothes are free. So if a bear has 6 clothes, then the two cheapest ones will be free and it would count as 4 accessories (see above).
     * - Inking on a specific bear is free if and only if the bear without discounts applied to it costs more than $70. 
     * TIP: the implemented savings method in the BearWorkshop1-5 do not use the getCost method implemented in this base class. They implement their own savings calculation
     *  		 All of them do however use the getRawCost method implemented in this base class. 
     * EXAMPLE: You buy 3 bears, one bear has 3 clothing items, the other two have 4 clothing items. Non of them have embroidery or noise makers and they have the same stuffing. 
     * Now, on each bear one clothing item will be free, since buy 2 get 1 free on a bear. So for costs we have the bear with stuffing. For one we pay only 2 clothing items, for 2 we still pay for 3 clothing items. 
     * Since all clothing is the same priece the bear with only 2 paid clothing items is cheapest. So we will get that bear for free. We will only have to pay for 2 bears, with 3 clothing items each. 
     * @return the savings if the customer would check with the current bears in the workshop out as double
     */
    public double calculateSavings() {
        //System.out.println("TODO: Implement me in Assignment 3");
        
        List<Bear> cart = this.BearCart; // Make reference to a BearCart
        
        double eachBearSavings = 0; // Start a savings for each individual bear at zero
        double allBearsSavings = 0; // Start a savings for all the bears together at zero
        
        // Iterate thru all bears in the BearCart
        for (int i = 0; i < cart.size(); i++) {
            Bear bear = cart.get(i); // Select each bear
            double rawCost = getRawCost(bear); // Find raw cost of each bear
            double cost = getCost(bear); // Find the cost of each bear
            System.out.println(rawCost + " <- raw cost of bear " + i);
            System.out.println(cost + " <- actual cost of bear " + i);
            eachBearSavings += rawCost - cost;
            System.out.println(eachBearSavings + " <<< each savings at");
        }
        
        return eachBearSavings;
    }
    
 
}