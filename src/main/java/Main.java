/*
File:  Main.java
Author:  SER 316
Date:  Fall B 2021
  
Description:
*/

package main.java;

import main.java.Stuffing.Stuff;

public class Main {

    public static void main(String[] args) {
        // Fill me in!
        @SuppressWarnings("URF_UNREAD_FIELD") 

        Bear bear1 = new Bear(Stuff.FOAM);   
        Bear bear2 = new Bear(Stuff.BASE);
        Bear bear3 = new Bear(Stuff.DOWN);

        BearWorkshop workshop = new BearWorkshop("AZ");
        workshop.addBear(bear2);
        workshop.addBear(bear3);
        workshop.addBear(bear1);

        System.out.println(bear1.price);

        System.out.println(workshop.getCost(bear2));
        System.out.println(bear2.price);

        System.out.println(workshop.getCost(bear3));
        System.out.println(bear3.price);

        bear1.clothing.add(new Clothing());

        System.out.println("Bear with NO clothing object");
        System.out.println(bear1.price);

        System.out.println("Bear with one clothing object");
        System.out.println(workshop.getRawCost(bear1));

        bear1.clothing.add(new Clothing(5, "Cool sunglasses"));
        System.out.println("Bear with two clothing object");
        System.out.println(workshop.getRawCost(bear1));
    }
}
