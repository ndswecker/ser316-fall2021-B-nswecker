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
        assertEquals(threeBearsExpected, ans);
    }
    
    //nswecker test
    @Test
    public void largeOrderBuy999get333Free() {
    	// Test if large numbers work as expected. Only pay for 666 bears
    	BearWorkshop largeOrderBears = null;
    	try {
    		largeOrderBears = createBearWorkshop("Bulkistan");
    	} catch (Exception e) {
    		
    	}
    	for (int i = 0; i <= 333; i++) {
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.BASE));
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.DOWN));
    		largeOrderBears.addBear(new Bear(Stuffing.stuffing.FOAM));
    	}

    	largeOrderExpected = 19980.00; 
    	
    	Double ans = largeOrderBears.calculateSavings();
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
    
    // sample test
    
    @Test
    public void oneBearTest14clothings() {
    	// test if the 10 percent discount will work
        BearWorkshop bears = null;
        try {
            bears = createBearWorkshop("VT"); // the 14th state
        } catch (Exception e) {
        }
        
        Bear customBear = new Bear(Stuffing.stuffing.BASE); // $31
        bears.addBear(customBear);
        
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        
        for (int i = 0; i < 15; i++) {
            String generatedString = new String(array, Charset.forName("UTF-8"));
    	    customBear.clothing.add(new Clothing(4, generatedString)); //$35
        }

	    
        Double bearsExpected = 4.0 * 4; // 4 free clothing items
        Double ans = bears.calculateSavings();
        assertEquals(bearsExpected, ans, 0.005);
    }
    
}
