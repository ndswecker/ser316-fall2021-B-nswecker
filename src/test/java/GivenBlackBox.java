import main.java.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import main.java.BearWorkshop;

import static org.junit.Assert.*;

/***
 * This class provides a framework to implement black box tests for various
 * implementations of the BearWorkshop Class. The BearWorkshop is having a
 * blowout sale and is offering the following savings.
 */
@RunWith(Parameterized.class)
public class GivenBlackBox {
    private Class<BearWorkshop> classUnderTest;

    @SuppressWarnings("unchecked")
    public GivenBlackBox(Object classUnderTest) {
        this.classUnderTest = (Class<BearWorkshop>) classUnderTest;
    }

    @Parameters
    public static Collection<Object[]> courseGradesUnderTest() {
        Object[][] classes = {
                {BearWorkshop1.class},
                {BearWorkshop2.class},
                {BearWorkshop3.class},
                {BearWorkshop4.class},
                {BearWorkshop5.class}

        };
        return Arrays.asList(classes);
    }

    private BearWorkshop createBearWorkshop(String name) throws Exception {
        Constructor<BearWorkshop> constructor = classUnderTest.getConstructor(String.class);
        return constructor.newInstance(name);
    }

    BearWorkshop oneBear;
    Double oneBearExpected;

    BearWorkshop threeBears;
    Double threeBearsExpected;

    BearWorkshop twoBears;
    Double twoBearsExpected;
    
    BearWorkshop largeOrderBears;
    Double largeOrderExpected;
    
    BearWorkshop freeInkBears;
    Double freeInkExpected;

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    // sample test

    /**
     * Test examines a BearFactory with 1 simple bear in it. The bear costs $30
     * and there are no savings.
     */
    @Test
    public void oneBearNoSavings() {
    	// One Bear base stuffing, no saving expected
        
        BearWorkshop oneBear = null;
        try {
        	oneBear = createBearWorkshop("NY");
        } catch (Exception e) {
        }
        
        oneBear.addBear(new Bear(Stuffing.stuffing.BASE)); // $30 stuffing + $1 casing -- should be no savings at all
        oneBearExpected = 0.00; // no savings since no clothing
    	
        Double ans = oneBear.calculateSavings();
        //System.out.println("oneBear.calculateSavings() --> " + ans);
        assertEquals(oneBearExpected, ans);
    }


    // sample test
    @Test
    public void threeBearsSaveOnCheapest() {
    	 // Three Bears expected to not pay for cheapest one
    	BearWorkshop threeBears = null;
        try {
        	threeBears = createBearWorkshop("AZ");
        } catch (Exception e) {
        }
    	
        threeBears.addBear(new Bear(Stuffing.stuffing.BASE)); // this is the cheapest one
        threeBears.addBear(new Bear(Stuffing.stuffing.DOWN));
        threeBears.addBear(new Bear(Stuffing.stuffing.FOAM));
        threeBearsExpected = 31.00;

        Double ans = threeBears.calculateSavings();
        //System.out.println("threeBears.calculateSavings() --> " + ans);
        assertEquals(threeBearsExpected, ans);
    }
    
    /** Test if ordering a large number of bears effects the discount equation.
     * Order 999 bears and expect 333 of them to be free. Test method expects the 
     * cheapest 333 to be free.
     *  */
    @Test
    public void largeOrderBuy999get333Free() {
    	// Test if large numbers work as expected. 999, only pay for 666 bears
    	BearWorkshop largeOrderBears = null;
    	try {
    		largeOrderBears = createBearWorkshop("Bulkistan");
    	} catch (Exception e) {
    		
    	}
    	int i;
    	for (i = 0; i < 333; i++) {
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.BASE)); // 31
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.DOWN)); // 41
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.FOAM)); // 51
    	}
    	
    	// 41*333 + 51*333 = 30636 (31*333 = 10323 savings)
    	
    	largeOrderExpected = 10323.0; 
    	Double ans = largeOrderBears.calculateSavings();
    	//System.out.println("largeOrderBears.calculateSavings() --> " + ans);
    	assertEquals(largeOrderExpected, ans);
    }

    // sample test
 
    @Test
    public void oneBearTest3clothings() {
        BearWorkshop bears = null;
        try {
            bears = createBearWorkshop("DC");
        } catch (Exception e) {
        }
        
        Bear customBear = new Bear(Stuffing.stuffing.BASE); // $31
        bears.addBear(customBear);

	    customBear.clothing.add(new Clothing(4, "Hat")); //$35
	    customBear.clothing.add(new Clothing(4, "Sunglasses")); //$39
	    customBear.clothing.add(new Clothing(4, "Shoes")); // free
	    
        Double bearsExpected = 4.0; // one cloth item for free
        Double ans = bears.calculateSavings();
        assertEquals(bearsExpected, ans, 0.005);
    }
    
    /**10% off discount if paid accessories are >= 10 items.
     * 14 is minimum number of accessories that would get the 10% discount.
     * The 4 cheapest would be free.
     * Method only tests if 10% discount applies when all items are the same cost. 
     * */
    @Test
    public void oneBearTest14clothings() {
    	// test if the 10 percent discount will work. 14 accessories means 4 are free, and 10 are paid
        BearWorkshop bears = null;
        try {
            bears = createBearWorkshop("VT"); // the 14th state
        } catch (Exception e) {
        }
        
        Bear customBear = new Bear(Stuffing.stuffing.BASE); // $31
        bears.addBear(customBear);
        
        for (int i = 0; i < 15; i++) {
            // Make a random name for each of the accessories
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
    	    customBear.clothing.add(new Clothing(4, generatedString)); //$35
        }
        
        // Base cost: 30 + 1 + 14*4 = 87
	    // Discount cost: 30 + 1 + 10*4 = 71
        // Expected savings: 87 - 71 = 16
        Double bearsExpected = 16.0; // 4 free clothing items
        Double ans = bears.calculateSavings();
        //System.out.println("14 Acc. savings --> " + ans);
        assertEquals(bearsExpected, ans, 0.005);
    }
    
    /** Test if a bear costing $70+ gets free embroidery.
     * Best way to make the bear +70 without other discounts is to give it
     * a single very expensive clothing accessory.
     *  */
    @Test
    public void freeInkTest() {
    	freeInkBears = null;
    	try {
    		freeInkBears = createBearWorkshop("InkLandia"); // The land of free ink
    	} catch (Exception e) {
    	}
    	Bear freeInkBear = new Bear(Stuffing.stuffing.FOAM); // $51
    	freeInkBears.addBear(freeInkBear);
    	freeInkBear.clothing.add(new Clothing(20, "Expensive Hat")); // $71
    	freeInkBear.ink = new Embroidery("I love you");
    	
    	Double inkDiscountExpected = 10.0; // 10 characters = $10
    	Double ans = freeInkBears.calculateSavings();
    	//System.out.println("Expensive Bear with Embroidery Savings --> " + ans);
    	assertEquals(inkDiscountExpected, ans);
    }
    
    @Test
    public void costInkTest() {
    	freeInkBears = null;
    	try {
    		freeInkBears = createBearWorkshop("Inkless");
    	} catch (Exception e) {
    	}
    	Bear costInkBear = new Bear(Stuffing.stuffing.BASE); // $31
    	costInkBear.ink = new Embroidery("I love you");
    	freeInkBears.addBear(costInkBear);
    	
    	Double inkSavings = 0.0;
    	Double ans = freeInkBears.calculateSavings();
    	//System.out.println("No savings expected for this basic bear -->" + ans);
    	assertEquals(inkSavings, ans);
    }
    
}
