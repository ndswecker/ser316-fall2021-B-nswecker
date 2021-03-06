/*
  File:	BearWorkshopInterface.java
  Author:	SER 316
  Date:	Fall B 2021
  
  Description:
*/

package main.java;

public interface BearWorkshopInterface {
	
	static final int CLOTHING_DISCOUNT = 3;
    
    public double getCost(Bear bear);
    public boolean addBear(Bear bear);
    public boolean removeBear(Bear bear);
    public double checkout();
    public double calculateTax();

}