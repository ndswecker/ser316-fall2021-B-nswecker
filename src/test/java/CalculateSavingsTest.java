
import main.java.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.java.Bear;
import main.java.BearWorkshop;
import main.java.Clothing;
import main.java.Customer;
import main.java.Embroidery;
import main.java.NoiseMaker;
import main.java.Stuffing;
import main.java.NoiseMaker.Location;

/**
 * Class: CalculateSavingsTest
 * 
 * Description: Method to test calculateSavings() from BearWorkshop
 */
public class CalculateSavingsTest {

    private BearWorkshop createBearWorkshop(String name) throws Exception {
        BearWorkshop shop = new BearWorkshop(name);
        return shop;
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

    BearWorkshop inkLengthBears;
    Double inkLengthExpected;

    BearWorkshop noisyBears;
    Double noiseExpected;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testThisClass() {
        System.out.println("CalculateSavings() is running. ");
    }

    /**
     * Test examines a BearFactory with 1 simple bear in it. The bear costs $30 and
     * there are no savings.
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
        // System.out.println("oneBear.calculateSavings() --> " + ans);
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
        // System.out.println("threeBears.calculateSavings() --> " + ans);
        assertEquals(threeBearsExpected, ans);
    }

    /**
     * Test if ordering a large number of bears effects the discount equation. Order
     * 999 bears and expect 333 of them to be free. Test method expects the cheapest
     * 333 to be free.
     */
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
        System.out.println("ans -> " + ans);
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

        customBear.clothing.add(new Clothing(4, "Hat")); // $35
        customBear.clothing.add(new Clothing(4, "Sunglasses")); // $39
        customBear.clothing.add(new Clothing(4, "Shoes")); // free

        Double bearsExpected = 4.0; // one cloth item for free
        Double ans = bears.calculateSavings();
        assertEquals(bearsExpected, ans, 0.005);
    }

    /**
     * 10% off discount if paid accessories are >= 10 items. 14 is minimum number of
     * accessories that would get the 10% discount. The 4 cheapest would be free.
     * Method only tests if 10% discount applies when all items are the same cost.
     */
    @Test
    public void oneBearTest14clothings() {
        // test if the 10 percent discount will work. 14 accessories means 4 are free,
        // and 10 are paid
        System.out.println("Testing 14 clothing items of $4 each");
        BearWorkshop bears = null;
        try {
            bears = createBearWorkshop("VT"); // the 14th state
        } catch (Exception e) {
        }

        Bear customBear = new Bear(Stuffing.stuffing.BASE); // $31
        bears.addBear(customBear);

        for (int i = 0; i < 14; i++) {
            // Make a random name for each of the accessories
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));
            customBear.clothing.add(new Clothing(4, generatedString)); // $35
        }

        // Base cost: 30 + 1 + 14*4 = 87
        // Discount cost: 30 + 1 + 10*4 = 71
        // Expected savings: 87 - 71 = 16
        Double bearsExpected = 16.0; // 4 free clothing items
        Double ans = bears.calculateSavings();

        System.out.println("14 Acc. savings --> " + ans + "\n");
        assertEquals(bearsExpected, ans, 0.005);
    }

    /**
     * Test if a bear costing $70+ gets free embroidery. Best way to make the bear
     * +70 without other discounts is to give it a single very expensive clothing
     * accessory.
     */
    @Test
    public void freeInkTest() {
        System.out.println("\nTesting free ink");
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
        System.out.println("Expensive Bear with Embroidery Savings --> " + ans);
        assertEquals(inkDiscountExpected, ans);
    }

    /**
     * Test if a bear costing less than $70 will incur an embroidery cost
     */
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
        // System.out.println("No savings expected for this basic bear -->" + ans);
        assertEquals(inkSavings, ans);
    }

    /**
     * Test that the cost for the length of embroidery is $1 per character Tests 1
     * thru 10 characters
     */
    @Test
    public void inkLengthCost() {
        System.out.println("\ninkLengthCost()");
        Double inkCost;
        inkLengthBears = null;
        try {
            inkLengthBears = createBearWorkshop("WA");
        } catch (Exception e) {
        }

        Bear inkLength1 = new Bear(Stuffing.stuffing.BASE); // $31
        inkLength1.ink = new Embroidery(randomLengthString(10));
        inkLengthBears.addBear(inkLength1);

        inkCost = 10 + 31.0;
        Double ans = inkLengthBears.getRawCost(inkLength1);
        System.out.println("ans -> " + ans + " =? " + inkCost);
        assertEquals(inkCost, ans);
    }

    /** Test embroidery length 0 */
    @Test
    public void inkLength0Cost() {
        Double inkCost;
        inkLengthBears = null;
        try {
            inkLengthBears = createBearWorkshop("WA");
        } catch (Exception e) {
        }

        Bear inkLength0 = new Bear(Stuffing.stuffing.BASE); // $31
        inkLength0.ink = new Embroidery(randomLengthString(0));
        inkLengthBears.addBear(inkLength0);

        inkCost = 0 + 31.0;
        Double ans = inkLengthBears.getRawCost(inkLength0);
        String message = "Length " + 0 + " caused failure";
        assertEquals(message, inkCost, ans);
    }

    /** Test embroidery length 1 */
    @Test
    public void inkLength1Cost() {
        Double inkCost;
        inkLengthBears = null;
        try {
            inkLengthBears = createBearWorkshop("WA");
        } catch (Exception e) {
        }

        Bear inkLength1 = new Bear(Stuffing.stuffing.BASE); // $31
        inkLength1.ink = new Embroidery(randomLengthString(1));
        inkLengthBears.addBear(inkLength1);

        inkCost = 1 + 31.0;
        Double ans = inkLengthBears.getRawCost(inkLength1);
        String message = "Length " + 1 + " caused failure";
        assertEquals(message, inkCost, ans);
    }

    /** Test embroidery length 5 */
    @Test
    public void inkLength5Cost() {
        Double inkCost;
        inkLengthBears = null;
        try {
            inkLengthBears = createBearWorkshop("WA");
        } catch (Exception e) {
        }

        Bear inkLength5 = new Bear(Stuffing.stuffing.BASE); // $31
        inkLength5.ink = new Embroidery(randomLengthString(5));
        inkLengthBears.addBear(inkLength5);

        inkCost = 5 + 31.0;
        Double ans = inkLengthBears.getRawCost(inkLength5);
        String message = "Length " + 5 + " caused failure";
        assertEquals(message, inkCost, ans);
    }

    /** Test embroidery length 10 */
    @Test
    public void inkLength10Cost() {
        System.out.println("\ninkLengthCost10()");
        Double inkCost;
        inkLengthBears = null;
        try {
            inkLengthBears = createBearWorkshop("WA");
        } catch (Exception e) {
        }

        Bear inkLength10 = new Bear(Stuffing.stuffing.BASE); // $31
        inkLength10.ink = new Embroidery(randomLengthString(10));
        inkLengthBears.addBear(inkLength10);

        inkCost = 10 + 31.0;
        Double ans = inkLengthBears.getRawCost(inkLength10);
        System.out.println("ans -> " + ans + " =? " + inkCost);
        assertEquals(inkCost, ans);
    }

    private String randomLengthString(int r) {
        // Make a random name for each of the accessories
        byte[] array = new byte[r]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    /** Test how much a center noise maker adds to the raw cost of a bear */
    @Test
    public void NoiseMakerCenter() {
        noisyBears = null;
        try {
            noisyBears = createBearWorkshop("BoomLand");
        } catch (Exception e) {
        }

        // Bear loudBear = new Bear(Stuffing.stuffing.BASE); // $31
        // loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase",
        // Location.CENTERBODY));
        Bear loudBear = testAnyNoiseMaker(1);
        noisyBears.addBear(loudBear);

        Double noiseCost = 41.0;
        Double ans = noisyBears.getRawCost(loudBear);
        // System.out.println("Bear with centerbody noise maker cost --> " + ans + "
        // Expected: " + noiseCost);

        assertEquals(noiseCost, ans);
    }

    /** Test how much a limb noise maker adds to the raw cost of a bear */
    @Test
    public void NoiseMakerOuter() {
        noisyBears = null;
        try {
            noisyBears = createBearWorkshop("BoomLand");
        } catch (Exception e) {
        }

        // Bear loudBear = new Bear(Stuffing.stuffing.BASE); // $31
        // loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase",
        // Location.CENTERBODY));
        Bear loudBear = testAnyNoiseMaker(2);
        noisyBears.addBear(loudBear);

        Double noiseCost = 36.0;
        Double ans = noisyBears.getRawCost(loudBear);
        // System.out.println("Bear with centerbody noise maker cost --> " + ans + "
        // Expected: " + noiseCost);

        assertEquals(noiseCost, ans);
    }

    /**
     * Test how much a center and limb noise maker adds to the raw cost of a bear
     */
    @Test
    public void NoiseMakerMultiple() {
        noisyBears = null;
        try {
            noisyBears = createBearWorkshop("BoomLand");
        } catch (Exception e) {
        }

        Bear loudBear = new Bear(Stuffing.stuffing.BASE); // $31
        int[] multiNoise = { 1, 2, 0 };
        loudBear = testAnyNoiseMaker(multiNoise, loudBear);
        noisyBears.addBear(loudBear);

        Double noiseCost = 46.0;
        Double ans = noisyBears.getRawCost(loudBear);
        // System.out.println("Bear with centerbody noise maker cost --> " + ans + "
        // Expected: " + noiseCost);

        assertEquals(noiseCost, ans);
    }

    /** Test how much two limb noises maker adds to the raw cost of a bear */
    @Test
    public void NoiseMakerMultipleLimbs() {
        noisyBears = null;
        try {
            noisyBears = createBearWorkshop("BoomLand");
        } catch (Exception e) {
        }

        Bear loudBear = new Bear(Stuffing.stuffing.BASE); // $31
        int[] multiNoise = { 0, 2, 3 };
        loudBear = testAnyNoiseMaker(multiNoise, loudBear);
        noisyBears.addBear(loudBear);

        Double noiseCost = 41.0;
        Double ans = noisyBears.getRawCost(loudBear);
        // System.out.println("Bear with centerbody noise maker cost --> " + ans + "
        // Expected: " + noiseCost);

        assertEquals(noiseCost, ans);
    }

    private Bear testAnyNoiseMaker(int locale) {
        Bear loudBear = new Bear(Stuffing.stuffing.BASE); // $31
        if (locale == 1) {
            loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.CENTERBODY));
        } else if (locale == 2) {
            loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.RIGHT_HAND));
        } else if (locale == 3) {
            loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.RIGHT_FOOT));
        } else if (locale == 4) {
            loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.LEFT_HAND));
        } else if (locale == 5) {
            loudBear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.LEFT_FOOT));
        }
        return loudBear;
    }

    private Bear testAnyNoiseMaker(int[] locale, Bear bear) {
        if (locale[0] == 1) {
            bear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.CENTERBODY));
        }
        if (locale[1] == 2 || locale[2] == 2) {
            bear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.RIGHT_HAND));
        }
        if (locale[1] == 3 || locale[2] == 3) {
            bear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.RIGHT_FOOT));
        }
        if (locale[1] == 4 || locale[2] == 4) {
            bear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.LEFT_HAND));
        }
        if (locale[1] == 5 || locale[2] == 5) {
            bear.addNoise(new NoiseMaker("testLabel", "testPhrase", Location.LEFT_FOOT));
        }
        return bear;
    }

    @Test
    public void customerFullMake() {
        Customer test = new Customer(20, "TE", null);
        assertEquals(test.state, "TE");
    }

}
